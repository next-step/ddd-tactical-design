package kitchenpos.order.eatinorder.domain.model;


import static kitchenpos.order.eatinorder.exception.EatInOrderExceptionMessage.EAT_IN_ORDER_EMPTY_ORDER_LINE_ITEM_EXCEPTION;
import static kitchenpos.order.eatinorder.exception.EatInOrderExceptionMessage.EAT_IN_ORDER_FLOW_EXCEPTION;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.order.common.model.OrderLineItem;

@Table(name = "eat_in_orders")
@Entity
public class EatInOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "eat_in_order_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_eat_in_orders")
    )
    private List<OrderLineItem> orderLineItems;

    @ManyToOne
    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_eat_in_orders_to_order_table")
    )
    private OrderTable orderTable;

    @Enumerated
    private EatInOrderFlow eatInOrderFlow;

    @Transient
    private UUID orderTableId;

    public EatInOrder() {
    }

    public EatInOrder(UUID id, LocalDateTime orderDateTime,
                      List<OrderLineItem> orderLineItems, EatInOrderFlow eatInOrderFlow) {
        validateOrderLineItemIsEmpty(orderLineItems);
        this.id = id;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.eatInOrderFlow = eatInOrderFlow;
    }

    private void validateOrderLineItemIsEmpty(List<OrderLineItem> orderLineItems) {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException(EAT_IN_ORDER_EMPTY_ORDER_LINE_ITEM_EXCEPTION.getMessage());
        }
    }

    public void processOrderFlow(EatInOrderStatus orderStatus) {
        if (!eatInOrderFlow.validateOrderStatus(orderStatus)) {
            throw new IllegalStateException(EAT_IN_ORDER_FLOW_EXCEPTION.getMessage());
        }
        this.eatInOrderFlow = EatInOrderFlow.findByOrderStatus(orderStatus);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public EatInOrderFlow getEatInOrderFlow() {
        return eatInOrderFlow;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public void occupyOrderTable(final OrderTable orderTable) {
        orderTable.validateTableIsOccupied();
        this.orderTable = orderTable;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
