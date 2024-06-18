package kitchenpos.eatinorders.domain.eatinorder;

import kitchenpos.Fixtures;
import kitchenpos.common.domain.ProfanityValidator;
import kitchenpos.eatinorders.domain.menu.MenuClient;
import kitchenpos.eatinorders.infra.DefaultMenuClient;
import kitchenpos.eatinorders.infra.FakeMenuClient;
import kitchenpos.menus.domain.tobe.menu.Menu;
import kitchenpos.menus.domain.tobe.menu.MenuProducts;
import kitchenpos.menus.domain.tobe.menu.MenuRepository;
import kitchenpos.menus.domain.tobe.menu.ProductClient;
import kitchenpos.menus.infra.DefaultProductClient;
import kitchenpos.menus.infra.InMemoryMenuRepository;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProductRepository;
import kitchenpos.products.infra.FakeProfanityValidator;
import kitchenpos.products.infra.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OrderLineItemsTest {
  private ProfanityValidator profanityValidator;
  private MenuProducts menuProducts;

  public static ProductRepository productRepository;
  public static ProductClient productClient;
  public static FakeMenuClient menuClient;
  public static MenuRepository menuRepository;
  public static Product product;
  public static Menu menu;
  @BeforeEach
  void setUp() {
    menuRepository = new InMemoryMenuRepository();
    menuClient = new FakeMenuClient();
    profanityValidator = new FakeProfanityValidator();
    productRepository = new InMemoryProductRepository();
    productClient = new DefaultProductClient(productRepository);
    product = Fixtures.product();
    productRepository.save(product);
    menuProducts = MenuProducts.of(productClient, Fixtures.menuProduct(product, 2));

    menu = Menu.of("후라이드+후라이드", 20_000L, menuGroup().getId(), true, menuProducts, profanityValidator);
  }


  @DisplayName("주문 메뉴 리스트를 생성할 수 있다.")
  @Test
  void creatOrderLineItems() {
    OrderLineItem item1 = OrderLineItem.of(menu.getId(), 20_000L, 2L);

    OrderLineItems actual = OrderLineItems.of(menuClient, List.of(orderLineItem(menuClient)));

    assertAll(
            () -> assertThat(actual).isNotNull(),
            () -> assertThat(actual.getSize()).isEqualTo(1)
    );
  }


  @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
  @Test
  void validateMenuPrice() {
    assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> OrderLineItems.of(menuClient, List.of(orderLineItem(menuClient, BigDecimal.valueOf(10_000_000L)))))
            .withMessageContaining("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.");
  }

  @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
  @Test
  void validateHiddenMenu() {

    menu.hide();
    OrderLineItem item1 = OrderLineItem.of(menu.getId(), 10_000L, 2L);

    assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> OrderLineItems.of(menuClient, List.of(orderLineItem(menuClient, false))))
            .withMessageContaining("숨겨진 메뉴는 주문할 수 없다.");
  }
}
