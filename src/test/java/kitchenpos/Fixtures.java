package kitchenpos;

import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.tobe.domain.ToBeOrder;
import kitchenpos.eatinorders.tobe.domain.ToBeOrderLineItem;
import kitchenpos.eatinorders.domain.OrderLineItem;

import kitchenpos.menus.tobe.domain.ToBeMenu;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.ToBeMenuProduct;
import kitchenpos.products.domain.Product;
import kitchenpos.products.infra.DefaultPurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ToBeProduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class Fixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static ToBeMenu menu(PurgomalumClient purgomalumClient) {
        return menu(19_000L, true, menuProduct(purgomalumClient));
    }

    public static ToBeMenu menu(final long price, final ToBeMenuProduct... menuProducts) {
        return menu(price, false, menuProducts);
    }

    public static ToBeMenu menu(final long price, final boolean displayed, final ToBeMenuProduct... menuProducts) {
        final ToBeMenu menu = new ToBeMenu();
        menu.setId(UUID.randomUUID());
        menu.setName("후라이드+후라이드");
        menu.setPrice(BigDecimal.valueOf(price));
        menu.setMenuGroup(menuGroup());
        menu.setDisplayed(displayed);
        menu.setMenuProducts(Arrays.asList(menuProducts));
        return menu;
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        final MenuGroup menuGroup = new MenuGroup();
        menuGroup.setId(UUID.randomUUID());
        menuGroup.setName(name);
        return menuGroup;
    }

    public static ToBeMenuProduct menuProduct(PurgomalumClient purgomalumClient) {
        final ToBeMenuProduct menuProduct = new ToBeMenuProduct();
        menuProduct.setSeq(new Random().nextLong());
        menuProduct.setProduct(product(purgomalumClient));
        menuProduct.setQuantity(2L);
        return menuProduct;
    }

    public static ToBeMenuProduct menuProduct(final ToBeProduct product, final long quantity) {
        final ToBeMenuProduct menuProduct = new ToBeMenuProduct();
        menuProduct.setSeq(new Random().nextLong());
        menuProduct.setProduct(product);
        menuProduct.setQuantity(quantity);
        return menuProduct;
    }

    public static ToBeOrder order(final OrderStatus status, final String deliveryAddress, PurgomalumClient purgomalumClient) {
        final ToBeOrder order = new ToBeOrder();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.DELIVERY);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem(purgomalumClient)));
        order.setDeliveryAddress(deliveryAddress);
        return order;
    }

    public static ToBeOrder order(final OrderStatus status, PurgomalumClient purgomalumClient) {
        final ToBeOrder order = new ToBeOrder();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.TAKEOUT);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem(purgomalumClient)));
        return order;
    }

    public static ToBeOrder order(final OrderStatus status, final OrderTable orderTable, PurgomalumClient purgomalumClient) {
        final ToBeOrder order = new ToBeOrder();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.EAT_IN);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem(purgomalumClient)));
        order.setOrderTable(orderTable);
        return order;
    }

    public static ToBeOrderLineItem orderLineItem(PurgomalumClient purgomalumClient) {
        final ToBeOrderLineItem orderLineItem = new ToBeOrderLineItem();
        orderLineItem.setSeq(new Random().nextLong());
        orderLineItem.setMenu(menu(purgomalumClient));
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

    public static ToBeProduct product(PurgomalumClient purgomalumClient) {
        return product("후라이드", 16_000L, purgomalumClient);
    }

    public static ToBeProduct product(final String name, final long price, PurgomalumClient purgomalumClient) {
        final ToBeProduct product = new ToBeProduct(name, BigDecimal.valueOf(price), purgomalumClient);
        return product;
    }
}
