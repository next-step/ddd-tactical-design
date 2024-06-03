package kitchenpos.eatinorders.todo.domain.orders;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kitchenpos.support.domain.OrderLineItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "eat_in_orders")
@Entity
public class EatInOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "eat_in_order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_eat_in_orders")
    )
    private List<OrderLineItem> orderLineItems;

    @Column(name = "order_table_id", nullable = false, columnDefinition = "binary(16)")
    private UUID orderTableId;

    protected EatInOrder() {
    }

    public EatInOrder(List<OrderLineItem> orderLineItems, UUID orderTableId, OrderTableClient orderTableClient) {
        this(EatInOrderStatus.WAITING, orderLineItems, orderTableId);
        validateOrderTable(orderTableId, orderTableClient);
    }

    public EatInOrder(EatInOrderStatus status, List<OrderLineItem> orderLineItems, UUID orderTableId) {
        this.id = UUID.randomUUID();
        this.status = status;
        this.orderDateTime = LocalDateTime.now();
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public void setStatus(final EatInOrderStatus status) {
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

    public UUID getOrderTableId() {
        return orderTableId;
    }

    private void validateOrderTable(UUID orderTableId, OrderTableClient orderTableClient) {
        EatInOrderPolicy policy = new EatInOrderPolicy(orderTableClient);
        policy.checkClearOrderTable(orderTableId);
    }
}
