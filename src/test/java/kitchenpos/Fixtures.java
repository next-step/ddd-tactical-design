package kitchenpos;

import kitchenpos.eatinorders.domain.*;
import kitchenpos.menus.tobe.domain.entity.IncludedProduct;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;
import kitchenpos.menus.tobe.domain.vo.MenuGroupName;
import kitchenpos.menus.tobe.domain.vo.MenuProductQuantity;
import kitchenpos.products.application.FakePurgomalumValidator;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.common.domain.vo.Name;
import kitchenpos.common.domain.vo.Price;

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
        Price priceVo = new Price(BigDecimal.valueOf(price));

        final Menu menu = new Menu(priceVo, new Name("후라이드+후라이드", new FakePurgomalumValidator()), displayed, menuGroup());
        for (MenuProduct menuProduct : menuProducts) {
            menu.getMenuProducts().add(menuProduct);
        }
        return menu;
    }


    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return new MenuGroup(new MenuGroupName(name));
    }

    public static MenuProduct menuProduct() {
        return new MenuProduct(new MenuProductQuantity(2L), includedProduct());
    }

    public static MenuProduct menuProduct(final IncludedProduct includedProduct, final long quantity) {
        return new MenuProduct(new MenuProductQuantity(quantity), includedProduct);
    }

    public static IncludedProduct includedProduct() {
        return new IncludedProduct(new Price(BigDecimal.valueOf(16_000L)));
    }

    public static IncludedProduct includedProduct(final long price) {
        return new IncludedProduct(new Price(BigDecimal.valueOf(price)));
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
        orderLineItem.setMenu(new kitchenpos.menus.domain.Menu());
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
        return new Product(new Name(name, new FakePurgomalumValidator()), new Price(BigDecimal.valueOf(price)));
    }
}
