package kitchenpos.products.tobe.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.domain.menu.Menu;
import kitchenpos.menus.domain.menu.MenuProduct;
import kitchenpos.menus.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.MenuFixtures;
import kitchenpos.menus.tobe.application.InMemoryMenuRepository;
import kitchenpos.products.application.ProductRequest;
import kitchenpos.products.application.ProductResponse;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.PurgomalumClient;
import kitchenpos.products.tobe.ProductFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  private ProductRepository productRepository;
  private MenuRepository menuRepository;
  private PurgomalumClient purgomalumClient;
  private ProductService productService;
  private FakeApplicationEventPublisher fakeApplicationEventPublisher;

  @BeforeEach
  void setUp() {
    productRepository = new InMemoryProductRepository();
    menuRepository = new InMemoryMenuRepository();
    purgomalumClient = new FakePurgomalumClient();
    fakeApplicationEventPublisher = new FakeApplicationEventPublisher(menuRepository);
    productService =
        new ProductService(productRepository, purgomalumClient, fakeApplicationEventPublisher);
  }

  @DisplayName("상품을 등록할 수 있다.")
  @Test
  void create() {
    final ProductRequest expected = new ProductRequest("후라이드", BigDecimal.valueOf(16_000L));
    final ProductResponse actual = productService.create(expected);
    assertThat(actual).isNotNull();
    assertAll(
        () -> assertThat(actual.getId()).isNotNull(),
        () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
        () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()));
  }

  @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
  @ValueSource(strings = "-1000")
  @NullSource
  @ParameterizedTest
  void create(final BigDecimal price) {
    final ProductRequest expected = new ProductRequest("후라이드", price);
    assertThatThrownBy(() -> productService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
  @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
  @NullSource
  @ParameterizedTest
  void create(final String name) {
    final ProductRequest expected = new ProductRequest(name, BigDecimal.valueOf(16_000L));
    assertThatThrownBy(() -> productService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("상품의 가격을 변경할 수 있다.")
  @Test
  void changePrice() {
    final UUID productId =
        productRepository.save(ProductFixtures.product("후라이드", 16_000L, purgomalumClient)).getId();
    final ProductRequest expected = new ProductRequest("", BigDecimal.valueOf(15_000L));
    final ProductResponse actual = productService.changePrice(productId, expected);
    assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
  }

  @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
  @ValueSource(strings = "-1000")
  @NullSource
  @ParameterizedTest
  void changePrice(final BigDecimal price) {
    final UUID productId =
        productRepository.save(ProductFixtures.product("후라이드", 16_000L, purgomalumClient)).getId();
    final ProductRequest expected = new ProductRequest("", price);
    assertThatThrownBy(() -> productService.changePrice(productId, expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
  @Test
  void changePriceInMenu() {
    final Product product = ProductFixtures.product();
    productRepository.save(product);

    final MenuProduct menuProduct = MenuFixtures.menuProduct(product, 1L);
    final Menu menu = MenuFixtures.menu(16_000L, menuProduct);
    menuRepository.save(menu);

    final ProductRequest expected = new ProductRequest("", BigDecimal.valueOf(8_000L));
    productService.changePrice(product.getId(), expected);
    assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isFalse();
  }

  @DisplayName("상품의 목록을 조회할 수 있다.")
  @Test
  void findAll() {
    productRepository.save(ProductFixtures.product("후라이드", 16_000L, purgomalumClient));
    productRepository.save(ProductFixtures.product("양념치킨", 16_000L, purgomalumClient));
    final List<ProductResponse> actual = productService.findAll();
    assertThat(actual).hasSize(2);
  }
}
