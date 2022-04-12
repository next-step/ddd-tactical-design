package kitchenpos;

import kitchenpos.support.policy.FakeSuccessNamingRule;
import kitchenpos.support.policy.FakeSuccessPricingRule;
import kitchenpos.support.policy.NamingRule;
import kitchenpos.support.policy.PricingRule;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.tobe.domain.TobeMenu;
import kitchenpos.menus.domain.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.domain.tobe.domain.TobeMenuProduct;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.tobe.domain.TobeProduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static kitchenpos.products.domain.tobe.domain.TobeProduct.Builder;

public class Fixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

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
        return new Builder().name(name).namingRule(new FakeSuccessNamingRule())
                .price(price).pricingRule(new FakeSuccessPricingRule()).build();
    }

    public static TobeProduct tobeProduct(final String name, final long price) {
        return new Builder().name(name).namingRule(new FakeSuccessNamingRule())
                .price(BigDecimal.valueOf(price)).pricingRule(new FakeSuccessPricingRule()).build();
    }

    public static TobeMenuGroup tobeMenuGroup(final String name) {
        return new TobeMenuGroup.MenuGroupBuilder().name(name).namingRule(new FakeSuccessNamingRule()).build();
    }

    public static TobeMenuGroup tobeMenuGroup(final String name, final NamingRule namingRule) {
        return new TobeMenuGroup.MenuGroupBuilder().name(name).namingRule(namingRule).build();
    }

    public static TobeMenu menu(final long price, final PricingRule pricingRule, final boolean displayed, final List<TobeMenuProduct> menuProducts) {
        final TobeMenu menu = new TobeMenu.Builder()
                .name("후라이드+후라이드")
                .namingRule(new FakeSuccessNamingRule())
                .price(BigDecimal.valueOf(price))
                .pricingRule(pricingRule)
                .displayed(displayed)
                .menuProducts(menuProducts)
                .menuGroup(tobeMenuGroup("메뉴그룹"))
                .build();
        return menu;
    }

    public static TobeMenuProduct tobeMenuProduct(final String name, final long price, final long quantity) {
        TobeProduct product = tobeProduct(name, price);
        return new TobeMenuProduct.Builder().product(product).quantity(quantity).productId(product.getId()).build();
    }

    public static List<TobeMenuProduct> tobeMenuProducts(final String name, final long price, final long quantity) {
        List<TobeMenuProduct> menuProducts = new ArrayList<>();
        TobeProduct product = tobeProduct(name, price);
        TobeMenuProduct menuProduct = new TobeMenuProduct.Builder()
                .product(product)
                .quantity(quantity)
                .productId(product.getId())
                .build();
        menuProducts.add(menuProduct);
        return menuProducts;
    }
}
