package kitchenpos;

import kitchenpos.common.domain.ProfanityValidator;
import kitchenpos.common.domain.orders.OrderStatus;
import kitchenpos.common.domain.ordertables.OrderType;
import kitchenpos.eatinorders.application.dto.OrderLineItemRequest;
import kitchenpos.eatinorders.application.dto.OrderRequest;
import kitchenpos.eatinorders.domain.eatinorder.Order;
import kitchenpos.eatinorders.domain.ordertable.OrderTable;
import kitchenpos.menus.domain.tobe.menu.Menu;
import kitchenpos.menus.domain.tobe.menu.MenuProduct;
import kitchenpos.menus.domain.tobe.menu.MenuProducts;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroup;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.infra.FakeProfanityValidator;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class Fixtures {
  public static final UUID INVALID_ID = new UUID(0L, 0L);
  public static ProfanityValidator profanityValidator = new FakeProfanityValidator();
  public static Product product = createProduct();

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

  public static OrderRequest order(final OrderStatus status, final String deliveryAddress) {
    final OrderRequest orderRequest = new OrderRequest();
    orderRequest.setId(UUID.randomUUID());
    orderRequest.setType(OrderType.DELIVERY);
    orderRequest.setStatus(status);
    orderRequest.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
    orderRequest.setOrderLineItems(Arrays.asList(orderLineItem()));
    orderRequest.setDeliveryAddress(deliveryAddress);
    return orderRequest;
  }

  public static OrderRequest order(final OrderStatus status) {
    final OrderRequest orderRequest = new OrderRequest();
    orderRequest.setId(UUID.randomUUID());
    orderRequest.setType(OrderType.TAKEOUT);
    orderRequest.setStatus(status);
    orderRequest.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
    orderRequest.setOrderLineItems(Arrays.asList(orderLineItem()));
    return orderRequest;
  }

  public static OrderRequest order(final OrderStatus status, final OrderTable orderTableRequest) {
    final Order orderRequest = new Order();
    orderRequest.setId(UUID.randomUUID());
    orderRequest.setType(OrderType.EAT_IN);
    orderRequest.setStatus(status);
    orderRequest.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
    orderRequest.setOrderLineItems(Arrays.asList(orderLineItem()));
    orderRequest.setOrderTable(orderTableRequest);
    return orderRequest;
  }

  public static OrderLineItemRequest orderLineItem() {
    final OrderLineItemRequest orderLineItemRequest = new OrderLineItemRequest();
    orderLineItemRequest.setSeq(new Random().nextLong());
    orderLineItemRequest.setMenu(menu());
    return orderLineItemRequest;
  }

  public static OrderTable orderTable() {
    return orderTable(false, 0);
  }

  public static OrderTable orderTable(final boolean occupied, final int numberOfGuests) {
    return OrderTable.of("1번", numberOfGuests);
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
