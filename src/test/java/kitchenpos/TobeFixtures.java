package kitchenpos;

import kitchenpos.eatinorders.domain.OrderTable;
import kitchenpos.menus.tobe.application.FakeMenuPurgomalumChecker;
import kitchenpos.menus.tobe.domain.menu.MenuName;
import kitchenpos.menus.tobe.domain.menu.MenuPrice;
import kitchenpos.menus.tobe.domain.menu.TobeMenu;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroupName;
import kitchenpos.menus.tobe.domain.menuproduct.MenuProductQuantity;
import kitchenpos.menus.tobe.domain.menuproduct.TobeMenuProduct;
import kitchenpos.products.application.FakePurgomalumChecker;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.TobeProduct;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class TobeFixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static TobeMenu menu() {
        return menu(19_000L, true, menuProduct());
    }

    public static TobeMenu menu(final long price, final TobeMenuProduct... menuProducts) {
        return menu(price, false, menuProducts);
    }

    public static TobeMenu menu(final long price, final boolean displayed, final TobeMenuProduct... menuProducts) {
        return new TobeMenu(UUID.randomUUID(), new MenuName("후라이드+후라이드", new FakeMenuPurgomalumChecker()),
                            new MenuPrice(BigDecimal.valueOf(price)), menuGroup(), displayed,
                            Arrays.asList(menuProducts));
    }

    public static TobeMenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static TobeMenuGroup menuGroup(final String name) {
        return new TobeMenuGroup(UUID.randomUUID(), new TobeMenuGroupName(name));
    }

    public static TobeMenuProduct menuProduct() {
        return new TobeMenuProduct(new Random().nextLong(), product(), new MenuProductQuantity(2L));
    }

    public static TobeMenuProduct menuProduct(final TobeProduct product, final long quantity) {
        return new TobeMenuProduct(new Random().nextLong(), product, new MenuProductQuantity(quantity));
    }

//    public static Order order(final OrderStatus status, final String deliveryAddress) {
//        final Order order = new Order();
//        order.setId(UUID.randomUUID());
//        order.setType(OrderType.DELIVERY);
//        order.setStatus(status);
//        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
//        order.setOrderLineItems(Arrays.asList(orderLineItem()));
//        order.setDeliveryAddress(deliveryAddress);
//        return order;
//    }

//    public static Order order(final OrderStatus status) {
//        final Order order = new Order();
//        order.setId(UUID.randomUUID());
//        order.setType(OrderType.TAKEOUT);
//        order.setStatus(status);
//        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
//        order.setOrderLineItems(Arrays.asList(orderLineItem()));
//        return order;
//    }

//    public static Order order(final OrderStatus status, final OrderTable orderTable) {
//        final Order order = new Order();
//        order.setId(UUID.randomUUID());
//        order.setType(OrderType.EAT_IN);
//        order.setStatus(status);
//        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
//        order.setOrderLineItems(Arrays.asList(orderLineItem()));
//        order.setOrderTable(orderTable);
//        return order;
//    }

//    public static OrderLineItem orderLineItem() {
//        final OrderLineItem orderLineItem = new OrderLineItem();
//        orderLineItem.setSeq(new Random().nextLong());
//        orderLineItem.setMenu(menu());
//        return orderLineItem;
//    }

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

    public static TobeProduct product() {
        return product("후라이드", 16_000L);
    }

    public static TobeProduct product(final String name, final long price) {
        return new TobeProduct(UUID.randomUUID(), new ProductName(name, new FakePurgomalumChecker()), new ProductPrice(BigDecimal.valueOf(price)));
    }
}
