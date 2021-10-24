package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.deliveryorders.infra.KitchenridersClient;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class TobeOrder extends AbstractAggregateRoot<TobeOrder> {
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
    private TobeOrderLineItems orderLineItems;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(
        name = "order_table_id",
        columnDefinition = "varbinary(16)",
        nullable = false
    )
    private UUID orderTableId;

    protected TobeOrder() {
    }

    public TobeOrder(OrderType type, TobeOrderLineItems orderLineItems, String deliveryAddress, UUID orderTableId) {
        validationOrderType(type);
        validationDeilveryAddress(type, deliveryAddress);
        validationEatInOrderTableId(type, orderTableId);
        this.id = UUID.randomUUID();
        this.type = type;
        this.status = OrderStatus.WAITING;
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

    public List<TobeOrderLineItem> getOrderLineItems() {
        return orderLineItems.getOrderLineItems();
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public void accept(KitchenridersClient kitchenridersClient) {
        validationAcceptOrderStatus();
        if (type == OrderType.DELIVERY) {
            kitchenridersClient.requestDelivery(id, orderLineItems.orderPrice(), deliveryAddress);
        }
        status = OrderStatus.ACCEPTED;
    }

    public void server() {
        validationServerOrderStatus();
        status = OrderStatus.SERVED;
    }

    public void startDelivery() {
        validationDeliveryOrderStatus();
        status = OrderStatus.DELIVERING;
    }

    public void completeDelivery() {
        validationCompleteDeliveryOrderStatus();
        status = OrderStatus.DELIVERED;
    }

    public void complete() {
        validationCompleteOrderStatus();
        status = OrderStatus.COMPLETED;
        if (type == OrderType.EAT_IN) {
            registerEvent(new CompleteEvent(id));
        }
    }

    private void validationCompleteDeliveryOrderStatus() {
        if (status != OrderStatus.DELIVERING) {
            throw new IllegalStateException("배송 중이 아닙니다.");
        }
    }

    private void validationServerOrderStatus() {
        if (status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("주문 승인 상태만 가능합니다.");
        }
    }

    private void validationAcceptOrderStatus() {
        if (status != OrderStatus.WAITING) {
            throw new IllegalStateException("주문 대기 상태만 가능합니다.");
        }
    }

    private void validationEatInOrderTableId(OrderType type, UUID orderTableId) {
        if (type == OrderType.EAT_IN && Objects.isNull(orderTableId)) {
            throw new IllegalArgumentException("주문테이블이 없습니다.");
        }
    }

    private void validationDeilveryAddress(OrderType type, String deliveryAddress) {
        if (type == OrderType.DELIVERY &&
                (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty())) {
            throw new IllegalArgumentException("배송지가 없습니다.");
        }
    }

    private void validationOrderType(OrderType type) {
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException();
        }
    }

    private void validationDeliveryOrderStatus() {
        if (type != OrderType.DELIVERY) {
            throw new IllegalStateException("배달 주문건이 아닙니다.");
        }
        if (status!= OrderStatus.SERVED) {
            throw new IllegalStateException("배송할 수 있는 상태가 아닙니다.");
        }
    }

    private void validationCompleteOrderStatus() {
        if (type == OrderType.DELIVERY && status != OrderStatus.DELIVERED) {
            throw new IllegalStateException("배송 완료가 안되었습니다.");
        }
        if (type != OrderType.DELIVERY && status != OrderStatus.SERVED) {
            throw new IllegalStateException("주문메뉴 서빙이 안되었습니다.");
        }
    }
}
