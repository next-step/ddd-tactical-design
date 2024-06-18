package kitchenpos;

import kitchenpos.common.domain.ProfanityValidator;
import kitchenpos.common.domain.orders.OrderStatus;
import kitchenpos.common.domain.ordertables.OrderType;
import kitchenpos.eatinorders.application.dto.OrderLineItemRequest;
import kitchenpos.eatinorders.application.dto.OrderRequest;
import kitchenpos.eatinorders.domain.eatinorder.*;
import kitchenpos.eatinorders.infra.FakeMenuClient;
import kitchenpos.menus.domain.tobe.menu.Menu;
import kitchenpos.menus.domain.tobe.menu.MenuProduct;
import kitchenpos.menus.domain.tobe.menu.MenuProducts;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroup;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.infra.FakeProfanityValidator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Fixtures {
  public static final UUID INVALID_ID = new UUID(0L, 0L);
  public static ProfanityValidator profanityValidator = new FakeProfanityValidator();
  public static Product product = createProduct();
  public static DefaultOrderFactory defaultOrderFactory = new DefaultOrderFactory();
  public static FakeMenuClient fakeMenuClient = new FakeMenuClient();
  public static Menu menu() {
    return menu(19_000L, true, menuProduct());
  }

  public static Menu menu(MenuProduct menuProduct) {
    MenuProducts menuProducts = MenuProducts.of(menuProduct);

    return menu(19_000L, true, menuProducts);
  }
  public static Menu menu(final long price, final MenuProducts menuProducts) {
    return menu(price, false, menuProducts);
  }
  public static Menu menu(final long price, final boolean displayed, final MenuProduct menuProduct) {

    MenuProducts menuProducts = MenuProducts.of(menuProduct);
    Menu menu = Menu.of("후라이드+후라이드", price, menuGroup().getId(), displayed, menuProducts, profanityValidator);

    return menu;
  }
  public static Menu menu(final long price, final boolean displayed, final MenuProducts menuProducts) {
    final Menu menu = Menu.of("후라이드+후라이드", price, menuGroup().getId(), displayed, menuProducts, profanityValidator);

    return menu;
  }

  public static MenuGroup menuGroup() {
    return menuGroup("두마리메뉴");
  }

  public static MenuGroup menuGroup(final String name) {
    return MenuGroup.of(UUID.randomUUID(), name);
  }

  public static MenuProduct menuProduct() {
    final MenuProduct menuProduct = MenuProduct.of(product().getId(),10_000L ,2L);
    return menuProduct;
  }

  public static MenuProduct menuProduct(final Product product, final long quantity) {
    final MenuProduct menuProduct = MenuProduct.of(product.getId(), 10_000L, quantity);

    return menuProduct;
  }

  public static Order order(final OrderStatus status, final String deliveryAddress) {
    return defaultOrderFactory.of(OrderType.DELIVERY, status, OrderLineItems.of(fakeMenuClient, List.of(orderLineItem())),
            null,deliveryAddress);
  }

  public static Order order(final OrderStatus status) {

    return defaultOrderFactory.of(OrderType.TAKEOUT, status, OrderLineItems.of(fakeMenuClient, List.of(orderLineItem())),
            null,"");
  }

  public static Order order(final OrderStatus status, final OrderTable orderTableRequest) {

    return defaultOrderFactory.of(OrderType.EAT_IN, status, OrderLineItems.of(fakeMenuClient, List.of(orderLineItem())),
            orderTableRequest, "");
  }

  public static OrderLineItem orderLineItem() {
    Menu menu = menu();
    fakeMenuClient.add(menu.getId(), menu.getMenuPrice(), menu.isDisplayed());
    return OrderLineItem.of(menu.getId(), menu.getMenuPrice().longValue(), 1L);
  }
  public static OrderLineItem orderLineItem(FakeMenuClient fakeMenuClient) {
    Menu menu = menu();
    fakeMenuClient.add(menu.getId(), menu.getMenuPrice(), menu.isDisplayed());
    return OrderLineItem.of(menu.getId(), menu.getMenuPrice().longValue(), 1L);
  }

  public static OrderLineItem orderLineItem(FakeMenuClient fakeMenuClient, boolean isDisplayed) {
    Menu menu = menu();
    fakeMenuClient.add(menu.getId(), menu.getMenuPrice(), isDisplayed);
    return OrderLineItem.of(menu.getId(), menu.getMenuPrice().longValue(), 1L);
  }

  public static OrderLineItem orderLineItem(FakeMenuClient fakeMenuClient, BigDecimal price) {
    Menu menu = menu();
    fakeMenuClient.add(menu.getId(), price, menu.isDisplayed());
    return OrderLineItem.of(menu.getId(), menu.getMenuPrice().longValue(), 1L);
  }
  public static OrderTable orderTable() {
    return orderTable(false, 0);
  }

  public static OrderTable orderTable(final boolean occupied, final int numberOfGuests) {
    return OrderTable.of(occupied,"1번", numberOfGuests);
  }

  public static Product product() {
    return Product.from("후라이드", 16_000L, profanityValidator);
  }

  public static Product product(final String name, final long price) {
    return Product.from(name, price, profanityValidator);
  }

  public static Product createProduct() {
    return Product.from("우동", 10_000L, new FakeProfanityValidator());
  }

  public static Product createProduct(String productName) {
    return Product.from(productName, 10_000L, new FakeProfanityValidator());
  }
}
