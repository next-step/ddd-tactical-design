package kitchenpos;

import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.deliveryorders.domain.DeliveryOrderLineItem;
import kitchenpos.deliveryorders.domain.DeliveryOrderLineItemPrice;
import kitchenpos.deliveryorders.domain.DeliveryOrderLineItemQuantity;
import kitchenpos.deliveryorders.domain.MenuClient;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.tobe.domain.order.EatInMenuClient;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItemPrice;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItemQuantity;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItems;
import kitchenpos.eatinorders.tobe.domain.ordertable.NumberOfGuests;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableName;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuName;
import kitchenpos.menus.tobe.domain.menu.MenuPrice;
import kitchenpos.menus.tobe.domain.menu.MenuProductPrice;
import kitchenpos.menus.tobe.domain.menu.MenuProductQuantity;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupName;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.application.InMemoryProductClient;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class Fixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);
    private static PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    public static Menu menu() {
        return menu(19_000L, true, menuProduct());
    }

    public static Menu menu(final long price, final MenuProduct... menuProducts) {
        return menu(price, false, menuProducts);
    }
    public static Menu displayedMenu(final long price, final MenuProduct... menuProducts) {
        return menu(price, true, menuProducts);
    }

    public static Menu menu(final long price, final boolean displayed, final MenuProduct... menuProducts) {
        final Menu menu = Menu.of(new MenuName("후라이드+후라이드", purgomalumClient), new MenuPrice(BigDecimal.valueOf(price)), menuGroup(), displayed, new MenuProducts(Arrays.asList(menuProducts)));
        return menu;
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        final MenuGroup menuGroup = MenuGroup.from(new MenuGroupName(name));
        return menuGroup;
    }

    public static MenuProduct menuProduct() {
        final InMemoryProductRepository productRepository = new InMemoryProductRepository();
        final MenuProduct menuProduct = MenuProduct.of(product().getId(), new MenuProductQuantity(2L), new InMemoryProductClient(productRepository));
        return menuProduct;
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        final MenuProduct menuProduct = new MenuProduct(product.getId(), new MenuProductQuantity(quantity), new MenuProductPrice(product.getPrice()));
        return menuProduct;
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

    public static EatInOrder eatInOrder(final OrderStatus status, final OrderTable orderTable, final Menu menu) {
        return new EatInOrder(UUID.randomUUID(), OrderType.EAT_IN, status, LocalDateTime.now(), new EatInOrderLineItems(Arrays.asList(new EatInOrderLineItem(menu.getId(), new EatInOrderLineItemQuantity(1L), new EatInOrderLineItemPrice(BigDecimal.TEN)))), orderTable);
    }

    public static OrderLineItem orderLineItem() {
        final OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setSeq(new Random().nextLong());
        orderLineItem.setMenu(menu());
        return orderLineItem;
    }

    public static DeliveryOrderLineItem deliveryOrderLineItem(Menu menu, MenuClient menuClient) {
        return DeliveryOrderLineItem.of(menu.getId(), new DeliveryOrderLineItemQuantity(2L), new DeliveryOrderLineItemPrice(menu.getPrice()), menuClient);
    }

    public static EatInOrderLineItem eatInOrderLineItem(Menu menu, EatInMenuClient menuClient) {
        return EatInOrderLineItem.of(menu.getId(), new EatInOrderLineItemQuantity(2L), new EatInOrderLineItemPrice(menu.getPrice()), menuClient);
    }

    public static OrderTable orderTable() {
        return orderTable(false, 0);
    }

    public static OrderTable occupiedOrderTable() {
        return orderTable(true, 0);
    }

    public static OrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        return new OrderTable(UUID.randomUUID(), new OrderTableName("1번"), new NumberOfGuests(numberOfGuests), occupied);
    }

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return Product.of(new ProductName(name, purgomalumClient), new ProductPrice(BigDecimal.valueOf(price)));
    }
}
