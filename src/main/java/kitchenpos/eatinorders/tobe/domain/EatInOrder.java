package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Table(name = "eat_in_order")
@Entity
public class EatInOrder {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private EatInOrderLineItems eatInOrderLineItems;

    private UUID orderTableId;

    protected EatInOrder() {

    }

    public EatInOrder(
        final UUID id,
        final OrderStatus status,
        final LocalDateTime orderDateTime,
        final EatInOrderLineItems eatInOrderLineItems,
        final UUID orderTableId
    ) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.eatInOrderLineItems = eatInOrderLineItems;
        this.orderTableId = orderTableId;
    }

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public EatInOrderLineItems getOrderLineItems() {
        return eatInOrderLineItems;
    }

    public List<EatInOrderLineItem> getOrderLineItemList() {
        return eatInOrderLineItems.getOrderLineItemList();
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public static EatInOrder create(
        final List<Menu> menus,
        final List<EatInOrderLineItem> eatInOrderLineItemList,
        final OrderTable orderTable
    ) {
        validateOrderTable(orderTable);
        return new EatInOrder(
            UUID.randomUUID(),
            OrderStatus.WAITING,
            LocalDateTime.now(),
            new EatInOrderLineItems(eatInOrderLineItemList, createMenuMap(menus)),
            orderTable.getId()
        );
    }

    private static void validateOrderTable(final OrderTable orderTable) {
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
    }

    private static Map<UUID, Menu> createMenuMap(final List<Menu> menus) {
        return menus.stream()
            .map(menu -> {
                validateDisplayedMenu(menu);
                return menu;
            })
            .collect(Collectors.toMap(
                Menu::getId,
                menu -> menu
            ));
    }

    private static void validateDisplayedMenu(final Menu menu) {
        if (!menu.isDisplayed()) {
            throw new IllegalStateException();
        }
    }

    public void accept() {
        if (status != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        status = OrderStatus.SERVED;
    }

    public void complete() {
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        status = OrderStatus.COMPLETED;
    }
}
