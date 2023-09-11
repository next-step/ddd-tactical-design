package kitchenpos.eatinorders.tobe.domain;



import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Table(name = "eat_in_orders")
@Entity
public class EatInOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "eat_in_orders_id",
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

    protected EatInOrder() { }

    public static EatInOrder of(List<OrderLineItem> orderLineItems, OrderTable orderTable) {
        return new EatInOrder(orderLineItems, orderTable);
    }


    public EatInOrder(List<OrderLineItem> orderLineItems, OrderTable orderTable) {
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

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public List<Long> getOrderLineItemSequence() {
        return orderLineItems.stream()
                .map(OrderLineItem::getSeq)
                .collect(Collectors.toList());
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public String getOrderTableId() {
        return orderTableId.toString();
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
