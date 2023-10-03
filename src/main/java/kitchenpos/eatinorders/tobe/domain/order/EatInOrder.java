package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.menus.tobe.domain.menu.Menu;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private EatInOrderLineItems orderLineItems;

    @ManyToOne
    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    protected EatInOrder() {
    }

    public EatInOrder(UUID id, OrderType type, OrderStatus status, LocalDateTime orderDateTime, EatInOrderLineItems orderLineItems, OrderTable orderTable) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTable = orderTable;
    }

    public static EatInOrder of(EatInOrderLineItems orderLineItems, OrderTable orderTable, EatInMenuClient eatInMenuClient) {
        validateEatInOrder(orderLineItems, eatInMenuClient);
        validateOrderTable(orderTable);
        return new EatInOrder(UUID.randomUUID(), OrderType.EAT_IN, OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, orderTable);
    }

    private static void validateOrderTable(OrderTable orderTable) {
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
    }

    private static void validateEatInOrder(EatInOrderLineItems orderLineItems, EatInMenuClient eatInMenuClient) {
        List<Menu> menus = eatInMenuClient.findAllByIdIn(
                orderLineItems.getOrderLineItems().stream()
                        .map(EatInOrderLineItem::getMenuId)
                        .collect(Collectors.toList())
        );
        if (menus.size() != orderLineItems.getOrderLineItems().size()) {
            throw new IllegalArgumentException();
        }
    }

    public void accept() {
        if (status != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        this.status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (status != OrderStatus.ACCEPTED) {
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

    public List<EatInOrderLineItem> getOrderLineItems() {
        return orderLineItems.getOrderLineItems();
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }
}
