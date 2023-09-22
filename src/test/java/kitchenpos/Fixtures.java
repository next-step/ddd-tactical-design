package kitchenpos;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.order.domain.*;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.domain.Product;

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
        MenuGroup menuGroup = menuGroup();
        return new Menu(UUID.randomUUID(), "후라이드+후라이드", BigDecimal.valueOf(price), menuGroup, displayed, Arrays.asList(menuProducts), menuGroup.getId(), new FakePurgomalumClient());
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(UUID id) {
        return new MenuGroup(id, "두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return new MenuGroup(UUID.randomUUID(), name);
    }

    public static MenuProduct menuProduct() {
        return new MenuProduct(product().getId(), BigDecimal.valueOf(16_000L), 2L);
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return new MenuProduct(product.getId(), product.getPrice(), quantity);

    }

    public static Order order(final OrderStatus status, final String deliveryAddress) {
        return new Order(UUID.randomUUID(), OrderType.DELIVERY, status, LocalDateTime.of(2020, 1, 1, 12, 0), new OrderLineItems(Arrays.asList(orderLineItem())), deliveryAddress, null, null);
    }

    public static Order order(final OrderStatus status) {
        return new Order(UUID.randomUUID(), OrderType.TAKEOUT, status, LocalDateTime.of(2020, 1, 1, 12, 0), new OrderLineItems(Arrays.asList(orderLineItem())), null, null, null);
    }

    public static Order order(final OrderStatus status, final OrderTable orderTable) {
        return new Order(UUID.randomUUID(), OrderType.EAT_IN, status, LocalDateTime.of(2020, 1, 1, 12, 0), new OrderLineItems(Arrays.asList(orderLineItem())), null, orderTable, orderTable.getId());
    }

    public static OrderLineItem orderLineItem() {
        return new OrderLineItem(menu().getId(), 2L, BigDecimal.valueOf(32_000L));
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

    public static Product product(final BigDecimal price) {
        return product("후라이드", price);
    }

    public static Product product(final String name, final BigDecimal price) {

        return new Product(UUID.randomUUID(), name, price, new FakePurgomalumClient());
    }

    public static Product product(final String name, final long price) {
        return new Product(UUID.randomUUID(), name, BigDecimal.valueOf(price), new FakePurgomalumClient());
    }
}
