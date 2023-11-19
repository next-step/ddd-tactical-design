package kitchenpos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import kitchenpos.common.Price;
import kitchenpos.menu.tobe.domain.Menu;
import kitchenpos.menu.tobe.domain.MenuProduct;
import kitchenpos.menu.tobe.domain.MenuProducts;
import kitchenpos.menugroup.domain.MenuGroup;
import kitchenpos.order.deliveryorder.domain.DeliveryOrder;
import kitchenpos.order.deliveryorder.domain.DeliveryOrderLineItem;
import kitchenpos.order.deliveryorder.domain.DeliveryOrderStatus;
import kitchenpos.order.takeoutorder.domain.TakeOutOrder;
import kitchenpos.order.takeoutorder.domain.TakeOutOrderLineItem;
import kitchenpos.order.takeoutorder.domain.TakeOutOrderStatus;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrder;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderLineItem;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderStatus;
import kitchenpos.order.tobe.eatinorder.domain.OrderTable;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductName;

public class Fixtures {

    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static Menu menu() {
        return menu(19_000L, true, menuProduct());
    }

    public static Menu menu(final long price, final MenuProduct... menuProducts) {
        return menu(price, false, menuProducts);
    }

    public static Menu menu(final long price, final boolean displayed, final MenuProduct... menuProducts) {
        return new Menu(UUID.randomUUID(), "후라이드+후라이드", new Price(BigDecimal.valueOf(price)), menuGroup().getId(),
            displayed, Arrays.asList(menuProducts));
    }

    public static Menu hideMenu(final long price, final MenuProduct... menuProducts) {
        Menu menu = menu(price, false, menuProducts);
        BigDecimal expensivePrice = BigDecimal.valueOf(price).subtract(BigDecimal.ONE);
        menu.changeMenuProductPrice(menuProducts[0].getProductId(), Price.of(expensivePrice));

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
        Product product = product();
        return new MenuProduct(product.getId(), product.getPrice(), 2L);
    }

    public static MenuProduct menuProduct(final long price, final long quantity) {
        return new MenuProduct(UUID.randomUUID(), new Price(BigDecimal.valueOf(price)), quantity);
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return new MenuProduct(product.getId(), product.getPrice(), quantity);
    }

    public static TakeOutOrder takeOutOrder(final TakeOutOrderStatus status) {
        final TakeOutOrder takeOutOrder = new TakeOutOrder();
        takeOutOrder.setId(UUID.randomUUID());
        takeOutOrder.setStatus(status);
        takeOutOrder.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        takeOutOrder.setOrderLineItems(Arrays.asList(takeOutOrderLineItem()));
        return takeOutOrder;
    }

    public static EatInOrder eatInOrder(final EatInOrderStatus status) {
        return new EatInOrder(status, LocalDateTime.of(2020, 1, 1, 12, 0), List.of(eatInOrderLineItem()), UUID.randomUUID());
    }

    public static EatInOrder eatInOrder(final EatInOrderStatus status, final OrderTable orderTable) {
        return new EatInOrder(status, LocalDateTime.of(2020, 1, 1, 12, 0), List.of(eatInOrderLineItem()), orderTable.getId());
    }

    public static DeliveryOrder deliveryOrder(final DeliveryOrderStatus status, final String deliveryAddress) {
        final DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setId(UUID.randomUUID());
        deliveryOrder.setStatus(status);
        deliveryOrder.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        deliveryOrder.setOrderLineItems(Arrays.asList(orderLineItem()));
        deliveryOrder.setDeliveryAddress(deliveryAddress);
        return deliveryOrder;
    }

    public static DeliveryOrder deliveryOrder(final DeliveryOrderStatus status) {
        final DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setId(UUID.randomUUID());
        deliveryOrder.setStatus(status);
        deliveryOrder.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        deliveryOrder.setOrderLineItems(Arrays.asList(orderLineItem()));
        return deliveryOrder;
    }

    public static DeliveryOrderLineItem orderLineItem() {
        final DeliveryOrderLineItem orderLineItem = new DeliveryOrderLineItem();
        orderLineItem.setSeq(new Random().nextLong());
        orderLineItem.setMenu(menu());
        return orderLineItem;
    }

    public static TakeOutOrderLineItem takeOutOrderLineItem() {
        final TakeOutOrderLineItem orderLineItem = new TakeOutOrderLineItem();
        orderLineItem.setSeq(new Random().nextLong());
        orderLineItem.setMenu(menu());
        return orderLineItem;
    }

    public static EatInOrderLineItem eatInOrderLineItem() {
        Menu menu = menu();
        return new EatInOrderLineItem(1, menu.getId(), Price.of(menu.getPrice()));
    }

    public static OrderTable emptyOrderTable() {
        return orderTable(false, 0);
    }

    public static OrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        return new OrderTable(UUID.randomUUID(), "1번", numberOfGuests, occupied);
    }

    public static Product product() {
        return product(16_000L);
    }

    public static Product product(final long price) {
        return product("후라이드", price);
    }

    public static Product product(final String name, final long price) {
        return new Product(UUID.randomUUID(), new ProductName(name), BigDecimal.valueOf(price));
    }

    public static MenuProducts menuProducts(MenuProduct... menuProducts) {
        return new MenuProducts(Arrays.asList(menuProducts));
    }
}
