package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static kitchenpos.eatinorders.tobe.domain.OrderStatus.*;

@Table(name = "orders")
@Entity
public class EatInOrder {
    private static final String EAT_IN_ORDER_CANNOT_REGISTERED_ON_EMPTY_TABLE = "빈 테이블에는 매장 주문을 등록할 수 없습니다.";
    private static final String NON_WAITING_ORDERS_CANNOT_BE_ACCEPTED = "접수 대기중인 주문만 접수할 수 있습니다.";
    private static final String NOT_ACCEPTED_EAT_IN_ORDERS_CANNOT_BE_SERVED = "매장 주문은 접수된 상태만 주문 서빙할 수 있습니다.";
    private static final String NOT_SERVED_EAT_IN_ORDERS_CANNOT_BE_COMPLETED = "매장 주문은 서빙된 상태만 주문 완료할 수 있습니다.";

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

    @Embedded
    private DeliveryAddress deliveryAddress;

    @ManyToOne
    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    protected EatInOrder() {
    }

    public EatInOrder(List<OrderLineItem> orderLineItems, OrderTable orderTable) {
        validateEatInOrder(orderTable);
        this.id = UUID.randomUUID();
        this.type = OrderType.EAT_IN;
        this.status = WAITING;
        this.orderDateTime = LocalDateTime.now();
        this.orderLineItems = new OrderLineItems(orderLineItems);
        this.orderTable = orderTable;
        this.orderTable.addEatInOrder(this);
    }

    private static void validateEatInOrder(OrderTable orderTable) {
        if (orderTable.isEmpty()) {
            throw new IllegalArgumentException(EAT_IN_ORDER_CANNOT_REGISTERED_ON_EMPTY_TABLE);
        }
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

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public void accept() {
        if (status != WAITING) {
            throw new IllegalStateException(NON_WAITING_ORDERS_CANNOT_BE_ACCEPTED);
        }
        this.status = ACCEPTED;
    }

    public void serve() {
        if (status != ACCEPTED) {
            throw new IllegalStateException(NOT_ACCEPTED_EAT_IN_ORDERS_CANNOT_BE_SERVED);
        }
        this.status = SERVED;
    }

    public void complete() {
        if (status != SERVED) {
            throw new IllegalStateException(NOT_SERVED_EAT_IN_ORDERS_CANNOT_BE_COMPLETED);
        }
        this.status = COMPLETED;
        if (orderTable.checkAllOrdersCompleted()) {
            orderTable.clear();
        }
    }

    public boolean hasCompleted() {
        return status.equals(COMPLETED);
    }
}
