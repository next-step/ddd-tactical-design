package kitchenpos.eatinorders.tobe.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class EatInOrder {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @ManyToOne
    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;


    protected EatInOrder() {

    }

    public EatInOrder(OrderLineItems orderLineItems, OrderTable orderTable) {
        this(UUID.randomUUID(), OrderType.EAT_IN, OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, orderTable);
    }

    public EatInOrder(UUID id, OrderType type, OrderStatus status, LocalDateTime orderDateTime, OrderLineItems orderLineItems, OrderTable orderTable) {
        checkOrderLineItem(orderLineItems);
        checkOrderTable(orderTable);
        this.id = id;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTable = orderTable;
    }

    private void checkOrderLineItem(OrderLineItems orderLineitems) {
        if (Objects.isNull(orderLineitems) || orderLineitems.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private void checkOrderTable(OrderTable orderTable) {
        if (Objects.isNull(orderTable) || orderTable.isOccupied()) {
            throw new IllegalArgumentException();
        }
    }

    public void Accepted() {
        if(!this.status.equals(OrderStatus.WAITING)){
            throw new IllegalStateException();
        }
        this.status = OrderStatus.ACCEPTED;
    }

    public void Served() {
        if(!this.status.equals(OrderStatus.ACCEPTED)){
            throw new IllegalStateException();
        }
        this.status = OrderStatus.SERVED;
    }

    public void Complete() {
        if(!this.status.equals(OrderStatus.SERVED)){
            throw new IllegalStateException();
        }
        this.status = OrderStatus.COMPLETED;

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

    public OrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrder that = (EatInOrder) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
