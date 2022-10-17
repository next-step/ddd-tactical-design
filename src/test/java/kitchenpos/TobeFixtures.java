package kitchenpos;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.OrderTableName;
import kitchenpos.menus.domain.*;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TobeFixtures {

    private static final PurgomalumClient client = new FakePurgomalumClient();

    public static Menu menu() {
        return menu(19_000L, true, menuProduct());
    }

    public static Menu menu(final long price, final MenuProduct... menuProducts) {
        return menu(price, false, menuProducts);
    }

    public static Menu menu(final long price, final boolean displayed, final MenuProduct... menuProducts) {
        return new Menu(new MenuName("후라이드+후라이드", client), new MenuPrice(BigDecimal.valueOf(price), BigDecimal.valueOf(price)), menuGroup(),displayed, new MenuProducts(Arrays.asList(menuProducts)));
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return new MenuGroup(new MenuGroupName(name));
    }

    public static MenuProduct menuProduct() {
        return new MenuProduct(new Random().nextLong(), product(), new MenuProductQuantity(2L));
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return new MenuProduct(new Random().nextLong(), product, new MenuProductQuantity(quantity));
    }

    public static Order order(final OrderStatus status, final String deliveryAddress) {
        return new Order(OrderType.DELIVERY, status, List.of(orderLineItem()), deliveryAddress, orderTable());
    }

    public static Order order(final OrderStatus status) {
        return new Order(OrderType.DELIVERY, status, List.of(orderLineItem()), "", orderTable());

    }

    public static Order order(final OrderStatus status, final OrderTable orderTable) {
        return new Order(OrderType.DELIVERY, status, List.of(orderLineItem()), "", orderTable);

    }

    public static OrderLineItem orderLineItem() {
        Menu menu = menu();
        return new OrderLineItem(menu, 1L, menu.priceValue());
    }
    public static OrderLineItem orderLineItem(long price) {
        Menu menu = menu(price, menuProduct());
        return new OrderLineItem(menu, 1L, menu.priceValue());
    }

    public static OrderTable orderTable() {
        return new OrderTable(new OrderTableName("1번"));
    }

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return new Product(UUID.randomUUID(), new ProductName(name), new ProductPrice(BigDecimal.valueOf(price)));
    }
}
