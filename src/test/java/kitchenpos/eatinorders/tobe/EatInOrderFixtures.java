package kitchenpos.eatinorders.tobe;

import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProducts;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.menuProduct;

public class EatInOrderFixtures {

    private static final UUID menuId = UUID.randomUUID();

    public static Menu menu() {
        return menu(menuId, true, 18_000L);
    }

    public static Menu menu(final boolean displayed) {
        return menu(menuId, displayed, 18_000L);
    }

    public static Menu menu(final long price) {
        return menu(menuId, true, price);
    }

    public static Menu menu(final UUID id, final boolean displayed, long price) {
        return Menu.create(
            id,
            "후라이드+후라이드",
            BigDecimal.valueOf(price),
            UUID.randomUUID(),
            displayed,
            new MenuProducts(List.of(menuProduct())),
            (name) -> false
        );
    }

    public static OrderLineItem orderLineItem() {
        return orderLineItem(18_000L, menuId);
    }

    public static OrderLineItem orderLineItem(final UUID menuId) {
        return OrderLineItem.create(1L, menuId, 18_000L);
    }

    public static OrderLineItem orderLineItem(final UUID menuId, final BigDecimal price) {
        return OrderLineItem.create(1L, menuId, price.toBigInteger().longValue());
    }


    public static OrderLineItem orderLineItem(final long quantity) {
        return OrderLineItem.create(quantity, menuId, 18_000L);
    }

    public static OrderLineItem orderLineItem(final Long price, final UUID menuId) {
        return OrderLineItem.create(1L, menuId, price);
    }

    public static OrderTable orderTable(final int numberOfGuests, final boolean occupied) {
        return new OrderTable(UUID.randomUUID(), new OrderTableName("1번"), new NumberOfGuests(numberOfGuests), occupied);
    }

    public static OrderTable orderTable() {
        return orderTable(4, true);
    }

    public static OrderTable orderTable(final boolean occupied) {
        return orderTable(4, occupied);
    }

    public static EatInOrder eatInOrder() {
        return EatInOrder.create(
            List.of(menu()),
            List.of(orderLineItem()),
            orderTable()
        );
    }

    public static EatInOrder eatInOrder(Menu menu) {
        return EatInOrder.create(
            List.of(menu),
            List.of(orderLineItem(menu.getId(), menu.getBigDecimalPrice())),
            orderTable()
        );
    }

    public static EatInOrder eatInOrder(OrderTable orderTable) {
        return EatInOrder.create(
            List.of(menu()),
            List.of(orderLineItem()),
            orderTable
        );
    }

    public static EatInOrder eatInOrder(Menu menu, OrderTable orderTable) {
        return EatInOrder.create(
            List.of(menu),
            List.of(orderLineItem(menu.getId(), menu.getBigDecimalPrice())),
            orderTable
        );
    }

    public static EatInOrder acceptedOrder() {
        EatInOrder eatInOrder = eatInOrder();
        eatInOrder.accept();
        return eatInOrder;
    }

    public static EatInOrder acceptedOrder(Menu menu) {
        EatInOrder eatInOrder = eatInOrder(menu);
        eatInOrder.accept();
        return eatInOrder;
    }

    public static EatInOrder acceptedOrder(OrderTable orderTable) {
        EatInOrder eatInOrder = eatInOrder(orderTable);
        eatInOrder.accept();
        return eatInOrder;
    }

    public static EatInOrder acceptedOrder(Menu menu, OrderTable orderTable) {
        EatInOrder eatInOrder = eatInOrder(menu, orderTable);
        eatInOrder.accept();
        return eatInOrder;
    }

    public static EatInOrder servedOrder() {
        EatInOrder eatInOrder = acceptedOrder();
        eatInOrder.serve();
        return eatInOrder;
    }

    public static EatInOrder servedOrder(Menu menu) {
        EatInOrder eatInOrder = acceptedOrder(menu);
        eatInOrder.serve();
        return eatInOrder;
    }

    public static EatInOrder servedOrder(Menu menu, OrderTable orderTable) {
        EatInOrder eatInOrder = acceptedOrder(menu, orderTable);
        eatInOrder.serve();
        return eatInOrder;
    }

    public static EatInOrder completedOrder() {
        EatInOrder eatInOrder = servedOrder();
        eatInOrder.complete();
        return eatInOrder;
    }

    public static EatInOrder completedOrder(Menu menu) {
        EatInOrder eatInOrder = servedOrder(menu);
        eatInOrder.complete();
        return eatInOrder;
    }
}
