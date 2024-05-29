package kitchenpos.domain.order.tobe.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "eat_in_order")
@Entity
public class EatInOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_eat_in_order_to_order_table")
    )
    private OrderTable orderTable;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "eat_in_order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_eat_in_order")
    )
    private List<OrderLineItem> orderLineItems;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    protected EatInOrder() {
    }

    public EatInOrder(OrderTable orderTable, List<OrderLineItem> orderLineItems) {
        if (orderLineItems == null || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("주문항목은 1개 이상이어야 합니다.");
        }
        this.orderLineItems = new ArrayList<>(orderLineItems);
        this.orderTable = orderTable;
        this.status = OrderStatus.WAITING;
    }

    public void accepted() {
        this.status = status.changeTo(OrderStatus.ACCEPTED);
    }

    public void serve() {
        this.status = status.changeTo(OrderStatus.SERVED);
    }

    public void complete() {
        this.status = status.changeTo(OrderStatus.COMPLETED);
        orderTable.clear();
    }

    public OrderTable orderTable() {
        return orderTable;
    }

    public List<OrderLineItem> orderLineItems() {
        return orderLineItems;
    }

    public OrderStatus status() {
        return status;
    }
}
