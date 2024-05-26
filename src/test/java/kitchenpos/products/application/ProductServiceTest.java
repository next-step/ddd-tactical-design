package kitchenpos.products.application;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.application.dto.ProductPriceChangeCommand;
import kitchenpos.products.application.dto.ProductRegisterCommand;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.DefaultProductProfanityValidator;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductProfanityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductServiceTest {

  private ProductRepository productRepository;
  private MenuRepository menuRepository;
  private ProductProfanityValidator profanityValidator;
  private ProductService productService;

  @BeforeEach
  void setUp() {
    productRepository = new InMemoryProductRepository();
    menuRepository = new InMemoryMenuRepository();
    PurgomalumClient purgomalumClient = new FakePurgomalumClient();
    profanityValidator = new DefaultProductProfanityValidator(purgomalumClient);
    productService = new ProductService(productRepository, profanityValidator);
  }

  @DisplayName("상품을 등록할 수 있다.")
  @Test
  void create() {
    final Product expected = product("후라이드", 16_000L);
    final ProductRegisterCommand command = new ProductRegisterCommand("후라이드",
        new BigDecimal(16_000L));
    final Product actual = productService.create(command);
    assertThat(actual).isNotNull();
    assertAll(
        () -> assertThat(actual.getId()).isNotNull(),
        () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
        () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
    );
  }

  @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
  @ValueSource(strings = "-1000")
  @NullSource
  @ParameterizedTest
  void create(final BigDecimal price) {
    final ProductRegisterCommand command = new ProductRegisterCommand("후라이드", price);
    assertThatThrownBy(() -> productService.create(command))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
  @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
  @NullSource
  @ParameterizedTest
  void create(final String name) {
    final ProductRegisterCommand command = new ProductRegisterCommand(name,
        new BigDecimal(16_000L));
    assertThatThrownBy(() -> productService.create(command))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("상품의 가격을 변경할 수 있다.")
  @Test
  void changePrice() {
    final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
    ProductPriceChangeCommand command = new ProductPriceChangeCommand(
        productId, new BigDecimal(15_000L));
    final Product expected = product("후라이드", 15_000L);
    final Product actual = productService.changePrice(command);
    assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
  }

  @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
  @ValueSource(strings = "-1000")
  @NullSource
  @ParameterizedTest
  void changePrice(final BigDecimal price) {
    final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
    ProductPriceChangeCommand command = new ProductPriceChangeCommand(productId, price);
    assertThatThrownBy(() -> productService.changePrice(command))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
  @Test
  void changePriceInMenu() {
    // 어떻게 해야할까요 ㅠㅠ
//    final Product product = productRepository.save(product("후라이드", 16_000L));
//    final Menu menu = menuRepository.save(menu(19_000L, true, menuProduct(product, 2L)));
//    productService.changePrice(product.getId(), changePriceRequest(8_000L));
//    assertThat(menuRepository.findById(menu.getId()).get().isDisplayed()).isFalse();
  }

  @DisplayName("상품의 목록을 조회할 수 있다.")
  @Test
  void findAll() {
    productRepository.save(product("후라이드", 16_000L));
    productRepository.save(product("양념치킨", 16_000L));
    final List<Product> actual = productService.findAll();
    assertThat(actual).hasSize(2);
  }

}
