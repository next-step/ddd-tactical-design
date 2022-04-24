package kitchenpos;

import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.domain.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.domain.tobe.domain.OrderLineItems;
import kitchenpos.eatinorders.domain.tobe.domain.TobeOrderLineItem;
import kitchenpos.eatinorders.domain.tobe.domain.TobeOrderTable;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuProducts;
import kitchenpos.menus.domain.tobe.domain.TobeMenu;
import kitchenpos.menus.domain.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.domain.tobe.domain.TobeMenuProduct;
import kitchenpos.menus.domain.tobe.domain.vo.MenuDisplayed;
import kitchenpos.menus.domain.tobe.domain.vo.MenuName;
import kitchenpos.menus.domain.tobe.domain.vo.MenuPrice;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.tobe.domain.TobeProduct;
import kitchenpos.products.domain.tobe.domain.vo.ProductName;
import kitchenpos.products.domain.tobe.domain.vo.ProductPrice;
import kitchenpos.support.infra.FakePurgomalumClient;
import kitchenpos.support.infra.profanity.Profanity;
import kitchenpos.support.vo.Quantity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static kitchenpos.products.domain.tobe.domain.TobeProduct.Builder;

public class Fixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);
    private static final Profanity profanity = new FakePurgomalumClient();

    public static Menu menu() {
        return menu(19_000L, true, menuProduct());
    }

    public static Menu menu(final long price, final MenuProduct... menuProducts) {
        return menu(price, false, menuProducts);
    }

    public static Menu menu(final long price, final boolean displayed, final MenuProduct... menuProducts) {
        final Menu menu = new Menu();
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

    public static MenuProduct menuProduct() {
        final MenuProduct menuProduct = new MenuProduct();
        menuProduct.setSeq(new Random().nextLong());
        menuProduct.setProduct(product());
        menuProduct.setQuantity(2L);
        return menuProduct;
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        final MenuProduct menuProduct = new MenuProduct();
        menuProduct.setSeq(new Random().nextLong());
        menuProduct.setProduct(product);
        menuProduct.setQuantity(quantity);
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

    public static EatInOrder eatInOrder(final TobeOrderTable orderTable) {
        return EatInOrder.Of(orderLineItems(), orderTable);
    }

    public static OrderLineItems orderLineItems() {
        final TobeMenu menu = tobeMenu();
        final TobeOrderLineItem orderLineItem = new TobeOrderLineItem(menu, menu.getPrice(), Quantity.One());
        return new OrderLineItems(Arrays.asList(orderLineItem));
    }

    public static OrderLineItem orderLineItem() {
        final OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setSeq(new Random().nextLong());
        orderLineItem.setMenu(menu());
        return orderLineItem;
    }

    public static OrderTable orderTable() {
        return orderTable(true, 0);
    }

    public static OrderTable orderTable(final boolean empty, final int numberOfGuests) {
        final OrderTable orderTable = new OrderTable();
        orderTable.setId(UUID.randomUUID());
        orderTable.setName("1번");
        orderTable.setNumberOfGuests(numberOfGuests);
        orderTable.setEmpty(empty);
        return orderTable;
    }

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        final Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName(name);
        product.setPrice(BigDecimal.valueOf(price));
        return product;
    }

    public static TobeProduct tobeProduct(final String name, final BigDecimal price) {
        return new Builder()
                .name(new ProductName(name, profanity))
                .price(new ProductPrice(price))
                .build();
    }

    public static TobeProduct tobeProduct(final String name, final long price) {
        return new Builder()
                .name(new ProductName(name, profanity))
                .price(new ProductPrice(BigDecimal.valueOf(price)))
                .build();
    }

    public static TobeMenuGroup tobeMenuGroup(final String name) {
        return new TobeMenuGroup.MenuGroupBuilder().name(name).build();
    }

    public static TobeMenu menu(final long price, final boolean displayed, final MenuProducts menuProducts) {
        final TobeMenu menu = new TobeMenu.Builder()
                .name(new MenuName("후라이드+후라이드", profanity))
                .price(new MenuPrice(BigDecimal.valueOf(price)))
                .displayed(new MenuDisplayed(displayed))
                .menuProducts(menuProducts)
                .menuGroup(tobeMenuGroup("메뉴그룹"))
                .build();
        return menu;
    }

    public static TobeMenu tobeMenu() {
        final TobeMenu menu = new TobeMenu.Builder()
                .name(new MenuName("후라이드+후라이드", profanity))
                .price(new MenuPrice(BigDecimal.valueOf(17_000)))
                .displayed(new MenuDisplayed(true))
                .menuProducts(tobeMenuProducts("후라이드",1,17_000))
                .menuGroup(tobeMenuGroup("메뉴그룹"))
                .build();
        return menu;
    }

    public static TobeMenuProduct tobeMenuProduct(final TobeProduct product, final long quantity) {
        return new TobeMenuProduct.Builder().product(product).quantity(quantity).productId(product.getId()).build();
    }

    public static MenuProducts tobeMenuProducts(final String name, final long price, final long quantity) {
        List<TobeMenuProduct> menuProducts = new ArrayList<>();
        TobeProduct product = tobeProduct(name, price);
        TobeMenuProduct menuProduct = new TobeMenuProduct.Builder()
                .product(product)
                .quantity(quantity)
                .productId(product.getId())
                .build();
        menuProducts.add(menuProduct);
        return new MenuProducts(menuProducts);
    }

    public static MenuProducts tobeMenuProducts(TobeProduct product, final long quantity) {
        List<TobeMenuProduct> menuProducts = new ArrayList<>();
        TobeMenuProduct menuProduct = new TobeMenuProduct.Builder()
                .product(product)
                .quantity(quantity)
                .productId(product.getId())
                .build();
        menuProducts.add(menuProduct);
        return new MenuProducts(menuProducts);
    }
}
