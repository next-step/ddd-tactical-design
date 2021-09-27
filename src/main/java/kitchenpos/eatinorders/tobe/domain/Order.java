package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableManager;
import kitchenpos.eatinorders.tobe.domain.service.OrderDeliverService;
import kitchenpos.eatinorders.tobe.exception.IllegalStatusChangeException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static kitchenpos.eatinorders.tobe.domain.OrderStatus.*;
import static kitchenpos.eatinorders.tobe.domain.OrderType.DELIVERY;
import static kitchenpos.eatinorders.tobe.domain.OrderType.EAT_IN;

@Table(name = "orders")
@Entity(name = "TobeOrder")
public class Order {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(
            name = "order_table_id",
            columnDefinition = "varbinary(16)",
            nullable = false
    )
    private UUID orderTableId;

    protected Order() {}

    public Order(final OrderType type, final OrderLineItems orderLineItems, final String deliveryAddress, final UUID orderTableId) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.status = WAITING;
        this.orderDateTime = LocalDateTime.now();
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
        this.orderTableId = orderTableId;
    }

    public UUID getId() {
        return id;
    }

    public OrderType getType() {
        return type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems.getOrderLineItems();
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public void accept(final OrderDeliverService orderDeliverService) {
        orderDeliverService.deliver(this);
        this.status = ACCEPTED;
    }

    public void serve() {
        if (status != ACCEPTED) {
            throw new IllegalStatusChangeException("접수되지 않은 주문은 서빙할 수 없습니다.");
        }
        this.status = SERVED;
    }

    public void startDelivery() {
        if (type != DELIVERY) {
            throw new IllegalStatusChangeException("배달 타입이 아닌 주문은 배달할 수 없습니다.");
        }
        if (status != SERVED) {
            throw new IllegalStatusChangeException("서빙되지 않은 주문은 배달할 수 없습니다.");
        }
        this.status = DELIVERING;
    }

    public void completeDelivery() {
        if (status != DELIVERING) {
            throw new IllegalStatusChangeException("배달 중이지 않은 주문은 배달 완료할 수 없습니다.");
        }
        this.status = DELIVERED;
    }

    public void complete(final OrderTableManager orderTableManager) {
        if (type == DELIVERY && status != OrderStatus.DELIVERED) {
            throw new IllegalStatusChangeException("배달 완료된 주문은 다시 완료할 수 없습니다.");
        }
        if ((type == OrderType.TAKEOUT || type == EAT_IN) && status != SERVED) {
            throw new IllegalStatusChangeException("서빙되지 않은 주문은 완료할 수 없습니다.");
        }
        this.status = COMPLETED;
        if (type == EAT_IN) {
            orderTableManager.clearOrderTable(orderTableId);
        }
    }
}
