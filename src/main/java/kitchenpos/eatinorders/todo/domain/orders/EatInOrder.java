package kitchenpos.eatinorders.todo.domain.orders;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import kitchenpos.eatinorders.exception.KitchenPosIllegalStateException;
import kitchenpos.support.domain.OrderLineItem;
import kitchenpos.support.domain.OrderLineItems;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.INVALID_ORDER_STATUS;

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

    @Embedded
    @JoinColumn(
        name = "eat_in_order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_eat_in_orders")
    )
    private OrderLineItems orderLineItems;

    @Column(name = "order_table_id", nullable = false, columnDefinition = "binary(16)")
    private UUID orderTableId;

    protected EatInOrder() {
    }

    protected EatInOrder(OrderLineItems orderLineItems, UUID orderTableId) {
        this(EatInOrderStatus.WAITING, orderLineItems, orderTableId);
    }

    public EatInOrder(EatInOrderStatus status, List<OrderLineItem> orderLineItems, UUID orderTableId) {
        this(status, new OrderLineItems(orderLineItems), orderTableId);
    }

    public EatInOrder(EatInOrderStatus status, OrderLineItems orderLineItems, UUID orderTableId) {
        this.id = UUID.randomUUID();
        this.status = status;
        this.orderDateTime = LocalDateTime.now();
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    public static EatInOrder create(OrderLineItems orderLineItems, UUID orderTableId, OrderTableClient orderTableClient) {
        validateOrderTable(orderTableId, orderTableClient);
        return new EatInOrder(orderLineItems, orderTableId);
    }

    public EatInOrder accept() {
        changeStatus(EatInOrderStatus.WAITING);
        return this;
    }

    public EatInOrder serve() {
        changeStatus(EatInOrderStatus.ACCEPTED);
        return this;
    }

    public EatInOrder complete(EatInOrderPolicy eatInOrderPolicy) {
        changeStatus(EatInOrderStatus.SERVED);
        eatInOrderPolicy.clearOrderTable(this.getOrderTableId());
        return this;
    }

    public UUID getId() {
        return id;
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems.getValues();
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    private static void validateOrderTable(UUID orderTableId, OrderTableClient orderTableClient) {
        EatInOrderTablePolicy policy = new EatInOrderTablePolicy(orderTableClient);
        policy.checkClearOrderTable(orderTableId);
    }

    private void changeStatus(EatInOrderStatus status) {
        if (this.status != status) {
            throw new KitchenPosIllegalStateException(INVALID_ORDER_STATUS, status);
        }
        this.status = status.next();
    }
}
