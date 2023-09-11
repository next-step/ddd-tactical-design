package kitchenpos;

import kitchenpos.eatinorders.tobe.domain.*;
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


    public static ToBeOrder order(final ToBeOrderStatus status, final ToBeOrderTable orderTable, PurgomalumClient purgomalumClient) {
        final ToBeOrder order = new ToBeOrder();
        ReflectionTestUtils.setField(order,"id",UUID.randomUUID());
        ReflectionTestUtils.setField(order,"status",status);
        ReflectionTestUtils.setField(order,"orderDateTime",LocalDateTime.of(2020, 1, 1, 12, 0));
        ReflectionTestUtils.setField(order,"orderLineItems",Arrays.asList(orderLineItem(purgomalumClient)));
        order.setOrderTable(orderTable);
        return order;
    }

    public static ToBeOrderLineItem orderLineItem(PurgomalumClient purgomalumClient) {
        final ToBeOrderLineItem orderLineItem = new ToBeOrderLineItem();
        ReflectionTestUtils.setField(orderLineItem,"seq",new Random().nextLong());
        ReflectionTestUtils.setField(orderLineItem,"menu",menu(purgomalumClient));
        return orderLineItem;
    }

    public static ToBeOrderTable orderTable() {
        return orderTable(false, 0);
    }

    public static ToBeOrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        final ToBeOrderTable orderTable = new ToBeOrderTable();
        ReflectionTestUtils.setField(orderTable,"id",UUID.randomUUID());
        ReflectionTestUtils.setField(orderTable,"name",new OrderTableName("1번"));
        ReflectionTestUtils.setField(orderTable,"numberOfGuests",new NumberOfGuests(numberOfGuests));
        ReflectionTestUtils.setField(orderTable,"occupied",occupied);

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
