package kitchenpos.products.application;

import kitchenpos.Fixtures;
import kitchenpos.common.domain.ProductPriceChangeEvent;
import kitchenpos.common.domain.ProfanityValidator;
import kitchenpos.menus.domain.tobe.menu.Menu;
import kitchenpos.menus.domain.tobe.menu.MenuRepository;
import kitchenpos.menus.infra.InMemoryMenuRepository;
import kitchenpos.products.application.dto.ProductRequest;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProductRepository;
import kitchenpos.products.infra.FakeProfanityValidator;
import kitchenpos.products.infra.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
@RecordApplicationEvents
@SpringBootTest
class ProductServiceTest {

  private ProductRepository productRepository;
  private MenuRepository menuRepository;
  private ProfanityValidator profanityValidator;
  private ProductService productService;
  @Autowired
  private ApplicationEventPublisher publisher;
  @Autowired
  private ApplicationEvents events;

  @BeforeEach
  void setUp() {
    productRepository = new InMemoryProductRepository();
    menuRepository = new InMemoryMenuRepository();
    profanityValidator = new FakeProfanityValidator();
    productService = new ProductService(productRepository, profanityValidator, publisher);
  }

  @DisplayName("상품을 등록할 수 있다.")
  @Test
  void create() {
    final ProductRequest expected = createProductRequest("후라이드", 16_000L);
    final Product actual = productService.create(expected);
    assertThat(actual).isNotNull();
    assertAll(
        () -> assertThat(actual.getId()).isNotNull(),
        () -> assertThat(actual.getProductName()).isEqualTo(expected.getName()),
        () -> assertThat(actual.getProductPrice()).isEqualTo(BigDecimal.valueOf(expected.getPrice()))
    );
  }

  @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
  @ValueSource(longs = -1000L)
  @NullSource
  @ParameterizedTest
  void create(final Long price) {
    final ProductRequest expected = createProductRequest("후라이드", price);
    assertThatThrownBy(() -> productService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
  @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
  @NullSource
  @ParameterizedTest
  void create(final String name) {
    final ProductRequest expected = createProductRequest(name, 16_000L);
    assertThatThrownBy(() -> productService.create(expected))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("상품의 가격을 변경할 수 있다.")
  @Test
  void changePrice() {
    final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
    final ProductRequest expected = changePriceRequest(15_000L);
    final Product actual = productService.changePrice(productId, expected);
    assertThat(actual.getProductPrice()).isEqualTo(BigDecimal.valueOf(expected.getPrice()));
  }

  @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
  @ValueSource(longs = -1000L)
  @NullSource
  @ParameterizedTest
  void changePrice(final Long price) {
    final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
    final ProductRequest expected = changePriceRequest(price);
    assertThatThrownBy(() -> productService.changePrice(productId, expected))
        .isInstanceOf(IllegalArgumentException.class);
  }




  @DisplayName("상품의 목록을 조회할 수 있다.")
  @Test
  void findAll() {
    productRepository.save(product("후라이드", 16_000L));
    productRepository.save(product("양념치킨", 16_000L));
    final List<Product> actual = productService.findAll();
    assertThat(actual).hasSize(2);
  }

  private ProductRequest createProductRequest(final String name, final Long price) {
    final ProductRequest product = new ProductRequest();
    product.setName(name);
    product.setPrice(price);
    return product;
  }

  private ProductRequest changePriceRequest(final Long price) {
    final ProductRequest product = new ProductRequest();
    product.setPrice(price);
    return product;
  }

}
