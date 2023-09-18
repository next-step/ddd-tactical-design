package kitchenpos;

import kitchenpos.eatinorders.domain.*;
import kitchenpos.menus.application.FakeMenuDisplayedNameProfanities;
import kitchenpos.menus.tobe.domain.menu.*;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupDisplayedName;
import kitchenpos.products.application.FakeProductDisplayedNameProfanities;
import kitchenpos.products.tobe.domain.ProductDisplayedNamePolicy;
import kitchenpos.products.tobe.domain.ProductDisplayedName;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
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
        return new Menu(
                UUID.randomUUID(),
                MenuDisplayedName.from("후라이드+후라이드", menuDisplayedNamePolicy()),
                MenuPrice.from(BigDecimal.valueOf(price)),
                menuGroup(),
                displayed,
                new MenuProducts(Arrays.asList(menuProducts))
        );
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return new MenuGroup(UUID.randomUUID(), new MenuGroupDisplayedName(name));
    }

    public static MenuProduct menuProduct() {
        final Product product = product();
        return new MenuProduct(
                new Random().nextLong(),
                product.getId(),
                product.getPrice().getValue(),
                2L
        );
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return new MenuProduct(
                new Random().nextLong(),
                product.getId(),
                product.getPrice().getValue(),
                quantity
        );
    }

    public static Order order(final OrderStatus status, final String deliveryAddress) {
        final Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.DELIVERY);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem()));
        order.setDeliveryAddress(deliveryAddress);
        return order;
    }

    public static Order order(final OrderStatus status) {
        final Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.TAKEOUT);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem()));
        return order;
    }

    public static Order order(final OrderStatus status, final OrderTable orderTable) {
        final Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.EAT_IN);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem()));
        order.setOrderTable(orderTable);
        return order;
    }

    public static OrderLineItem orderLineItem() {
        final OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setSeq(new Random().nextLong());
        orderLineItem.setMenu(menu());
        return orderLineItem;
    }

    public static OrderTable orderTable() {
        return orderTable(false, 0);
    }

    public static OrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        final OrderTable orderTable = new OrderTable();
        orderTable.setId(UUID.randomUUID());
        orderTable.setName("1번");
        orderTable.setNumberOfGuests(numberOfGuests);
        orderTable.setOccupied(occupied);
        return orderTable;
    }

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return new Product(UUID.randomUUID(), ProductDisplayedName.from(name, productDisplayedNamePolicy()), ProductPrice.from(BigDecimal.valueOf(price)));
    }

    public static ProductDisplayedNamePolicy productDisplayedNamePolicy() {
        return new ProductDisplayedNamePolicy(new FakeProductDisplayedNameProfanities());
    }

    public static MenuDisplayedNamePolicy menuDisplayedNamePolicy() {
        return new MenuDisplayedNamePolicy(new FakeMenuDisplayedNameProfanities());
    }
}
