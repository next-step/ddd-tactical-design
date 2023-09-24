package kitchenpos.takeoutorders.tobe;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.takeoutorders.tobe.domain.TakeOutOrder;
import kitchenpos.takeoutorders.tobe.domain.TakeOutOrderLineItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.menuProduct;

public class TakeOutOrderFixtures {

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

    public static TakeOutOrderLineItem orderLineItem() {
        return orderLineItem(18_000L, menuId);
    }

    public static TakeOutOrderLineItem orderLineItem(final UUID menuId) {
        return TakeOutOrderLineItem.create(1L, menuId, 18_000L);
    }

    public static TakeOutOrderLineItem orderLineItem(final UUID menuId, final BigDecimal price) {
        return TakeOutOrderLineItem.create(1L, menuId, price.toBigInteger().longValue());
    }


    public static TakeOutOrderLineItem orderLineItem(final long quantity) {
        return TakeOutOrderLineItem.create(quantity, menuId, 18_000L);
    }

    public static TakeOutOrderLineItem orderLineItem(final Long price, final UUID menuId) {
        return TakeOutOrderLineItem.create(1L, menuId, price);
    }

    public static TakeOutOrder takeOutOrder() {
        return TakeOutOrder.create(
            List.of(menu()),
            List.of(orderLineItem())
        );
    }

    public static TakeOutOrder takeOutOrder(Menu menu) {
        return TakeOutOrder.create(
            List.of(menu),
            List.of(orderLineItem(menu.getId(), menu.getBigDecimalPrice()))
        );
    }

    public static TakeOutOrder acceptedOrder() {
        TakeOutOrder takeOutOrder = takeOutOrder();
        takeOutOrder.accept();
        return takeOutOrder;
    }

    public static TakeOutOrder acceptedOrder(Menu menu) {
        TakeOutOrder takeOutOrder = takeOutOrder(menu);
        takeOutOrder.accept();
        return takeOutOrder;
    }

    public static TakeOutOrder servedOrder() {
        TakeOutOrder takeOutOrder = acceptedOrder();
        takeOutOrder.serve();
        return takeOutOrder;
    }

    public static TakeOutOrder servedOrder(Menu menu) {
        TakeOutOrder takeOutOrder = acceptedOrder(menu);
        takeOutOrder.serve();
        return takeOutOrder;
    }

    public static TakeOutOrder completedOrder() {
        TakeOutOrder takeOutOrder = servedOrder();
        takeOutOrder.complete();
        return takeOutOrder;
    }

    public static TakeOutOrder completedOrder(Menu menu) {
        TakeOutOrder takeOutOrder = servedOrder(menu);
        takeOutOrder.complete();
        return takeOutOrder;
    }
}
