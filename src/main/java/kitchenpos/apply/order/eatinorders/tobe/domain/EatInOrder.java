package kitchenpos.apply.order.eatinorders.tobe.domain;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "eat_in_orders")
@Entity
public class EatInOrder {
    @Column(name = "eat_in_orders_id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    protected EatInOrder() { }

    public static EatInOrder of(OrderLineItems orderLineItems, OrderTable orderTable) {
        return new EatInOrder(orderLineItems, orderTable);
    }

    public EatInOrder(OrderLineItems orderLineItems, OrderTable orderTable) {
        this.id = UUID.randomUUID();
        this.status = EatInOrderStatus.WAITING;
        this.orderDateTime = LocalDateTime.now();
        this.orderLineItems = orderLineItems;
        this.orderTable = orderTable;
    }

    public String getId() {
        return id.toString();
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<Long> getOrderLineItemSequence() {
        return orderLineItems.getOrderLineItemSequence();
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public String getOrderTableId() {
        return orderTable.getId();
    }

    public void accepted() {
        this.status = EatInOrderStatus.ACCEPTED;
    }

    public void serve() {
        this.status = EatInOrderStatus.SERVED;
    }

    public void complete() {
        this.status = EatInOrderStatus.COMPLETED;
    }
}
