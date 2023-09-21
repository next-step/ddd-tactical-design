package kitchenpos.takeoutorders.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Table(name = "take_out_order")
@Entity
public class TakeOutOrder {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    protected TakeOutOrder() {

    }

    public TakeOutOrder(
        final UUID id,
        final OrderStatus status,
        final LocalDateTime orderDateTime,
        final OrderLineItems orderLineItems
    ) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public OrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public static TakeOutOrder create(
        final List<Menu> menus,
        final List<OrderLineItem> orderLineItemList
    ) {
        return new TakeOutOrder(
            UUID.randomUUID(),
            OrderStatus.WAITING,
            LocalDateTime.now(),
            new OrderLineItems(orderLineItemList, createMenuMap(menus))
        );
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
