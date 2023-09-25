package kitchenpos.eatinorders.domain.order;

import kitchenpos.common.domain.OrderLineItem;
import kitchenpos.common.domain.OrderLineItems;
import kitchenpos.common.domain.code.OrderType;
import kitchenpos.eatinorders.domain.ordertable.OrderTable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class Order {
    @Column(name = "id", columnDefinition = "binary(16)")
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItem> orderLineItems;

    @ManyToOne
    @JoinColumn(
        name = "order_table_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    @Transient
    private UUID orderTableId;

    protected Order() {
    }

    public Order(final OrderLineItems orderLineItems, final OrderTable orderTable) {
        this.id = UUID.randomUUID();
        this.type = OrderType.EAT_IN;
        this.status = OrderStatus.WAITING;
        this.orderDateTime = LocalDateTime.now();
        this.orderLineItems = orderLineItems.getOrderLineItems();
        this.orderTable = orderTable;
    }

    public Order(final OrderType orderType, OrderStatus orderStatus, final LocalDateTime orderDateTime, final List<OrderLineItem> orderLineItems, final OrderTable orderTable) {
        this.id = UUID.randomUUID();
        this.type = orderType;
        this.status = orderStatus;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTable = orderTable;
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

    public void setStatus(final OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(final LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(final List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public void setOrderTable(final OrderTable orderTable) {
        this.orderTable = orderTable;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public void setOrderTableId(final UUID orderTableId) {
        this.orderTableId = orderTableId;
    }

    public void served() {
        validateOrderStatus(OrderStatus.ACCEPTED);
        this.status = OrderStatus.SERVED;
    }

    public void accept() {
        validateOrderStatus(OrderStatus.WAITING);
        this.status = OrderStatus.ACCEPTED;
    }

    public void complete() {
        validateOrderStatus(OrderStatus.SERVED);
        this.status = OrderStatus.COMPLETED;
        this.getOrderTable().clear();
    }

    private void validateOrderStatus(final OrderStatus status) {
        if (this.status != status) {
            throw new IllegalStateException("주문 상태가 " + status + "가 아닙니다.");
        }
    }
}
