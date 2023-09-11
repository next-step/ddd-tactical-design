package kitchenpos;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderTable;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.tobe.domain.ToBeOrder;
import kitchenpos.eatinorders.tobe.domain.ToBeOrderLineItem;
import kitchenpos.menus.tobe.domain.MenuProductQuantity;
import kitchenpos.menus.tobe.domain.ToBeMenu;
import kitchenpos.menus.tobe.domain.ToBeMenuGroup;
import kitchenpos.menus.tobe.domain.ToBeMenuProduct;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ToBeProduct;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class Fixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static ToBeMenu menu(PurgomalumClient purgomalumClient) {
        return menu(19_000L, true, purgomalumClient, menuProduct(purgomalumClient));
    }

    public static ToBeMenu menu(final long price,PurgomalumClient purgomalumClient ,final ToBeMenuProduct... menuProducts) {
        return menu(price, false,purgomalumClient , menuProducts);
    }

    public static ToBeMenu menu(final long price, final boolean displayed,PurgomalumClient purgomalumClient ,final ToBeMenuProduct... menuProducts) {
        final ToBeMenu menu = new ToBeMenu("후라이드+후라이드",price,menuGroup(),displayed,purgomalumClient,Arrays.asList(menuProducts));
        return menu;
    }

    public static ToBeMenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static ToBeMenuGroup menuGroup(final String name) {
        final ToBeMenuGroup menuGroup = new ToBeMenuGroup(name);
        return menuGroup;
    }

    public static ToBeMenuProduct menuProduct(PurgomalumClient purgomalumClient) {
        final ToBeMenuProduct menuProduct = menuProduct(product(purgomalumClient),2L);
        return menuProduct;
    }

    public static ToBeMenuProduct menuProduct(final ToBeProduct product, final long quantity) {
        final ToBeMenuProduct menuProduct = new ToBeMenuProduct();
        ReflectionTestUtils.setField(menuProduct,"seq",new Random().nextLong());
        ReflectionTestUtils.setField(menuProduct,"product",product);
        ReflectionTestUtils.setField(menuProduct,"quantity",new MenuProductQuantity(quantity));

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
