package kitchenpos;

import kitchenpos.eatinorders.domain.*;
import kitchenpos.menus.application.dto.MenuProductRequest;
import kitchenpos.menus.domain.tobe.menu.Menu;
import kitchenpos.menus.domain.tobe.menu.MenuProduct;
import kitchenpos.menus.domain.tobe.menu.MenuProducts;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroup;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProfanityValidator;
import kitchenpos.products.infra.FakeProfanityValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import kitchenpos.products.domain.tobe.ProfanityValidator;

public class Fixtures {
  public static final UUID INVALID_ID = new UUID(0L, 0L);
  public static ProfanityValidator profanityValidator = new FakeProfanityValidator();
  public static Product product = createProduct();

  public static Menu menu() {
    return menu(19_000L, true, menuProduct());
  }

  public static Menu menu(MenuProduct menuProduct) {
    MenuProducts menuProducts = MenuProducts.of();
    menuProducts.add(menuProduct);
    return menu(19_000L, true, menuProducts);
  }
  public static Menu menu(final long price, final MenuProducts menuProducts) {
    return menu(price, false, menuProducts);
  }
  public static Menu menu(final long price, final boolean displayed, final MenuProduct menuProduct) {
    MenuProducts menuProducts = MenuProducts.of();
    menuProducts.add(menuProduct);
    final Menu menu = Menu.of("후라이드+후라이드", price, menuGroup().getId(), displayed, menuProducts, profanityValidator);

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
    final MenuProduct menuProduct = MenuProduct.of(product().getId(),10_000L ,2);
    return menuProduct;
  }

  public static MenuProduct menuProduct(final Product product, final long quantity) {
    final MenuProduct menuProduct = MenuProduct.of(product.getId(), 10_000L, (int) quantity);

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
