package kitchenpos.eatinorders.tobe.eatinorder.domain;

import kitchenpos.common.domain.AbstractOrder;
import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "order")
@Entity
public class EatInOrder extends AbstractOrder {
    private static final OrderStatus DEFAULT_STATUS = OrderStatus.WAITING;

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItem> orderLineItems;

    @ManyToOne
    @JoinColumn(
        name = "order_table_id",
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    protected EatInOrder() {
    }

    @Override
    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public EatInOrder(final List<OrderLineItem> orderLineItems, final OrderTable orderTable) {
        this(UUID.randomUUID(), DEFAULT_STATUS, orderLineItems, orderTable);
    }

    private EatInOrder(final UUID id, final OrderStatus status, final List<OrderLineItem> orderLineItems, final OrderTable orderTable) {
        this.id = id;
        this.status = status;
        this.orderLineItems = orderLineItems;
        this.orderTable = orderTable;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EatInOrder that = (EatInOrder) o;
        return Objects.equals(id, that.id) || (status == that.status && Objects.equals(orderLineItems, that.orderLineItems) && Objects.equals(orderTable, that.orderTable));
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, orderLineItems, orderTable);
    }
}
