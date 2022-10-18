package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static kitchenpos.eatinorders.domain.OrderStatus.*;

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

    public Order() {
    }

    public Order(OrderType type, OrderStatus status, List<OrderLineItem> orderLineItems, OrderTable orderTable) {
        validateOrderType(type);
        validateOrderLineItems(orderLineItems);
        validateOrderTable(orderTable);

        this.id = UUID.randomUUID();
        this.type = type;
        this.status = status;
        this.orderDateTime = LocalDateTime.now();
        this.orderLineItems = orderLineItems;
        this.orderTable = orderTable;
    }

    public UUID id() {
        return id;
    }

    public OrderType type() {
        return type;
    }

    public OrderStatus status() {
        return status;
    }

    public LocalDateTime orderDateTime() {
        return orderDateTime;
    }

    public List<OrderLineItem> orderLineItems() {
        return orderLineItems;
    }

    public OrderTable orderTable() {
        return orderTable;
    }

    public void accept() {
        statusIsWaiting();
        this.status = ACCEPTED;
    }

    public void served() {
        statusIsAccept();
        this.status = SERVED;
    }

    public void completed() {
        statusIsServed();
        this.status = COMPLETED;
    }

    private void validateOrderType(OrderType type) {
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateOrderLineItems(List<OrderLineItem> orderLineItems) {
        for (OrderLineItem orderLineItem : orderLineItems) {
            validateMenuIsDisplayed(orderLineItem);
        }
    }

    private void validateOrderTable(OrderTable orderTable) {
        if (orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
    }

    private void statusIsWaiting() {
        if (this.status != WAITING) {
            throw new IllegalStateException();
        }
    }

    private void statusIsAccept() {
        if (this.status != ACCEPTED) {
            throw new IllegalStateException();
        }
    }

    private void statusIsServed() {
        if (this.status != SERVED) {
            throw new IllegalStateException();
        }
    }

    private void validateMenuIsDisplayed(OrderLineItem orderLineItem) {
        if (!orderLineItem.menuIsDisplayed()) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && type == order.type && status == order.status && Objects.equals(orderDateTime, order.orderDateTime) && Objects.equals(orderLineItems, order.orderLineItems) && Objects.equals(orderTable, order.orderTable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, status, orderDateTime, orderLineItems, orderTable);
    }
}
