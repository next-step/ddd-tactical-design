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
import kitchenpos.support.domain.MenuClient;
import kitchenpos.support.domain.OrderLineItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    private EatInOrder(List<OrderLineItem> orderLineItems, UUID orderTableId) {
        this(EatInOrderStatus.WAITING, orderLineItems, orderTableId);
    }

    public EatInOrder(EatInOrderStatus status, List<OrderLineItem> orderLineItems, UUID orderTableId) {
        this.id = UUID.randomUUID();
        this.status = status;
        this.orderDateTime = LocalDateTime.now();
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public static EatInOrder create(EatInOrder request, MenuClient menuClient,OrderTableClient orderTableClient) {
        validateOrderLineItems(request.getOrderLineItems(), menuClient);
        validateOrderTable(request.getOrderTableId(), orderTableClient);
        return new EatInOrder(mapping(request.getOrderLineItems(), menuClient), request.getOrderTableId());
    }

    public UUID getId() {
        return id;
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

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    private static void validateOrderTable(UUID orderTableId, OrderTableClient orderTableClient) {
        EatInOrderTablePolicy policy = new EatInOrderTablePolicy(orderTableClient);
        policy.checkClearOrderTable(orderTableId);
    }

    private static void validateOrderLineItems(List<OrderLineItem> orderLineItemRequests, MenuClient menuClient) {
        checkNullOrEmptyOrderLineItems(orderLineItemRequests);
        new EatInOrderOrderLineItemsPolicy(menuClient).checkDuplicatedMenu(orderLineItemRequests);
    }

    private static void checkNullOrEmptyOrderLineItems(List<OrderLineItem> orderLineItemRequests) {
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private static List<OrderLineItem> mapping(List<OrderLineItem> orderLineItems, MenuClient menuClient) {
        return orderLineItems.stream()
                .map(orderLineItem -> new OrderLineItem(orderLineItem, menuClient))
                .toList();
    }
}
