package kitchenpos.eatinorders.tobe.eatinorder.domain;

import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTableId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class EatInOrder {
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

    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "order_table_id"))
    )
    private OrderTableId orderTableId;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    protected EatInOrder() {
    }

    public EatInOrder(final List<OrderLineItem> orderLineItems, final UUID orderTableId) {
        this(UUID.randomUUID(), DEFAULT_STATUS, orderLineItems, new OrderTableId(orderTableId));
    }

    private EatInOrder(final UUID id, final OrderStatus status, final List<OrderLineItem> orderLineItems, final OrderTableId orderTableId) {
        this.id = id;
        this.status = status;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
        this.orderDateTime = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getOrderTableId() {
        return orderTableId.getId();
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void accept() {
        if(this.status != OrderStatus.WAITING) {
            throw new IllegalStateException("대기중인 주문만 수락할 수 있습니다.");
        }
        this.status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (this.status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        this.status = OrderStatus.SERVED;
    }

    public void complete() {
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        this.status = OrderStatus.COMPLETED;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EatInOrder that = (EatInOrder) o;
        return Objects.equals(id, that.id) || (status == that.status && Objects.equals(orderLineItems, that.orderLineItems) && Objects.equals(orderTableId, that.orderTableId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, orderLineItems, orderTableId);
    }
}
