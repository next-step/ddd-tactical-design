package kitchenpos.products.tobe.domain.service;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.dto.ProductChangePriceDto;
import kitchenpos.products.tobe.domain.dto.ProductCreateDto;
import kitchenpos.products.tobe.domain.dto.ProductDto;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.product2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductDomainServiceTest {
  private ProductRepository productRepository;
  private PurgomalumClient purgomalumClient;
  private ProductDomainService productDomainService;

  @BeforeEach
  void setUp() {
    productRepository = new InMemoryProductRepository();
    purgomalumClient = new FakePurgomalumClient();
    productDomainService = new ProductDomainService(productRepository, purgomalumClient);
  }

  @DisplayName("상품등록")
  @Nested
  class Create {
    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
      final ProductCreateDto expected = createProductRequest("후라이드", 16_000L);
      final ProductDto actual = productDomainService.create(expected);
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
      final ProductCreateDto expected = createProductRequest("후라이드", price);
      assertThatThrownBy(() -> productDomainService.create(expected))
          .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
      final ProductCreateDto expected = createProductRequest(name, 16_000L);
      assertThatThrownBy(() -> productDomainService.create(expected))
          .isInstanceOf(IllegalArgumentException.class);
    }
  }

  @DisplayName("상품가격변경")
  @Nested
  class ChangePrice {

    private UUID productId;

    @BeforeEach
    void setUp() {
      productId = productRepository.save(product2("후라이드", 16_000L)).getId();
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
      //given
      BigDecimal expected = new BigDecimal(15_000L);
      final ProductChangePriceDto request = changePriceRequest(productId, expected);

      //when
      final ProductDto actual = productDomainService.changePrice(request);
      //then
      assertThat(actual.getPrice()).isEqualTo(expected);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
      ProductChangePriceDto expected = changePriceRequest(productId, price);
      assertThatThrownBy(() -> productDomainService.changePrice(expected))
          .isInstanceOf(IllegalArgumentException.class);
    }
  }

  @DisplayName("상품의 목록을 조회할 수 있다.")
  @Test
  void findAll() {
    productRepository.save(product2("후라이드", 16_000L));
    productRepository.save(product2("양념치킨", 16_000L));
    final List<Product> actual = productDomainService.findAll();
    assertThat(actual).hasSize(2);
  }

  private ProductCreateDto createProductRequest(final String name, final long price) {
    return createProductRequest(name, BigDecimal.valueOf(price));
  }

  private ProductCreateDto createProductRequest(final String name, final BigDecimal price) {
    return new ProductCreateDto(name, price);
  }

  private ProductChangePriceDto changePriceRequest(UUID id, final BigDecimal price) {
    return new ProductChangePriceDto(id, price);
  }
}