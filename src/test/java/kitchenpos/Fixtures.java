package kitchenpos;

import kitchenpos.eatinorders.domain.tobe.EatInOrder;
import kitchenpos.eatinorders.domain.tobe.EatInOrderLineItem;
import kitchenpos.eatinorders.domain.tobe.OrderStatus;
import kitchenpos.eatinorders.domain.tobe.OrderTable;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPurgomalumClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class Fixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static Menu menu() {
        return menu(19_000L, true, menuProduct());
    }

    public static Menu menu(final long price, final MenuProduct... menuProducts) {
        return menu(price, false, menuProducts);
    }

    public static Menu menu(final long price, final boolean displayed, final MenuProduct... menuProducts) {
        return new Menu("후라이드+후라이드",
                text -> false,
                BigDecimal.valueOf(price),
                menuGroup(),
                displayed,
                Arrays.asList(menuProducts));
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return new MenuGroup(name);
    }

    public static MenuProduct menuProduct() {
        Product product = product();
        return new MenuProduct(product.getId(), product.getPrice(), 2L);
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return new MenuProduct(product.getId(), product.getPrice(), quantity);
    }

    public static EatInOrder order(final OrderStatus status, final OrderTable orderTable) {
        return new EatInOrder(
                UUID.randomUUID(),
                status,
                LocalDateTime.of(2020, 1, 1, 12, 0),
                Arrays.asList(orderLineItem()),
                orderTable
        );
    }

    public static EatInOrderLineItem orderLineItem() {
        Menu menu = menu();
        return new EatInOrderLineItem(menu.getId(), menu.getPrice(), 1);
    }

    public static OrderTable orderTable() {
        return orderTable(false, 0);
    }

    public static OrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        return new OrderTable(UUID.randomUUID(), "1번", numberOfGuests, occupied);
    }

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final Long price) {
        return new Product(name, new BigDecimal(price), text -> false);
    }

    public static Product product(final String name, final Long price, final ProductPurgomalumClient productPurgomalumClient) {
        return new Product(name, BigDecimal.valueOf(price), productPurgomalumClient);
    }

}
