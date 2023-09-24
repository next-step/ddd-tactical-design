package kitchenpos;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.application.TobeOrderTable;
import kitchenpos.eatinorders.tobe.domain.TobeOrder;
import kitchenpos.eatinorders.tobe.domain.TobeOrderLineItem;
import kitchenpos.menus.tobe.application.FakeMenuPurgomalumChecker;
import kitchenpos.menus.tobe.domain.menu.MenuName;
import kitchenpos.menus.tobe.domain.menu.MenuPrice;
import kitchenpos.menus.tobe.domain.menu.TobeMenu;
import kitchenpos.menus.tobe.domain.menu.TobeMenuProduct;
import kitchenpos.menus.tobe.domain.menu.TobeMenuProductQuantity;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroupName;
import kitchenpos.products.application.FakePurgomalumChecker;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.TobeProduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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
                            new MenuPrice(BigDecimal.valueOf(price)), menuGroup().getId(), displayed,
                            Arrays.asList(menuProducts));
    }

    public static TobeMenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static TobeMenuGroup menuGroup(final String name) {
        return new TobeMenuGroup(UUID.randomUUID(), new TobeMenuGroupName(name));
    }

    public static TobeMenuProduct menuProduct() {
        return new TobeMenuProduct(new Random().nextLong(), product().getId(),
                                   new MenuPrice(product().getPriceValue()), new TobeMenuProductQuantity(2L)
        );
    }

    public static TobeMenuProduct menuProduct(final TobeProduct product, final long quantity) {
        return new TobeMenuProduct(new Random().nextLong(), product.getId(),
                                   new MenuPrice(product().getPriceValue()), new TobeMenuProductQuantity(quantity)
        );
    }

    public static TobeProduct product() {
        return product("후라이드", 16_000L);
    }

    public static TobeProduct product(final String name, final long price) {
        return new TobeProduct(UUID.randomUUID(), new ProductName(name, new FakePurgomalumChecker()), new ProductPrice(BigDecimal.valueOf(price)));
    }

//    public static TobeOrder order(final OrderStatus status) {
//        final TobeOrder order = new TobeOrder(UUID.randomUUID(), status, LocalDateTime.of(2020, 1, 1, 12, 0),
//
//                                              );
//        order.setId(UUID.randomUUID());
//        order.setType(OrderType.DELIVERY);
//        order.setStatus(status);
//        order.setOrderDateTime();
//        order.setOrderLineItems(Arrays.asList(orderLineItem()));
//        return order;
//    }

    public static TobeOrder order(final OrderStatus status) {
        return new TobeOrder(UUID.randomUUID(), status, LocalDateTime.of(2020, 1, 1, 12, 0), UUID.randomUUID(),
                             List.of(orderLineItem()));
    }

    public static TobeOrder order(final OrderStatus status, final TobeOrderTable orderTable) {
        return new TobeOrder(UUID.randomUUID(), status, LocalDateTime.of(2020, 1, 1, 12, 0), orderTable.getId(),
                             List.of(orderLineItem()));
    }

    public static TobeOrderLineItem orderLineItem() {
        return new TobeOrderLineItem(new Random().nextLong(), menu().getId(), BigDecimal.valueOf(16_000));
    }

//    public static TobeOrderLineItem orderTable() {
//        return orderTable(false, 0);
//    }

    public static TobeOrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        return new TobeOrderTable(UUID.randomUUID(), "1번", numberOfGuests, occupied);
    }
}
