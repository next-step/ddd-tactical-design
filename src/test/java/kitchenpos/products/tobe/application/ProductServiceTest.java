package kitchenpos.products.tobe.application;

import kitchenpos.common.infra.FakeProfanities;
import kitchenpos.common.infra.Profanities;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.dto.ChangeProductPriceRequest;
import kitchenpos.products.tobe.dto.CreateProductRequest;
import kitchenpos.products.tobe.dto.ProductResponse;
import kitchenpos.products.tobe.infra.ProductTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceTest {
    private ProductRepository productRepository;
    private ProductTranslator productTranslator;
    private Profanities profanities;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        productTranslator = new InMemoryProductTranslator();
        profanities = new FakeProfanities();
        productService = new ProductService(productRepository, productTranslator, profanities);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final CreateProductRequest expected = new CreateProductRequest("후라이드", BigDecimal.valueOf(16_000L));
        final ProductResponse actual = productService.create(expected);
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
        final CreateProductRequest expected = new CreateProductRequest("후라이드", price);
        assertThatThrownBy(() -> productService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final CreateProductRequest expected = new CreateProductRequest(name, BigDecimal.valueOf(16_000L));
        assertThatThrownBy(() -> productService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID productId = productRepository.save(createProduct("후라이드", BigDecimal.valueOf(16_000L))).getId();
        final ChangeProductPriceRequest expected = new ChangeProductPriceRequest(BigDecimal.valueOf(15_000L));
        final ProductResponse actual = productService.changePrice(productId, expected);
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID productId = productRepository.save(createProduct("후라이드", BigDecimal.valueOf(16_000L))).getId();
        final ChangeProductPriceRequest expected = new ChangeProductPriceRequest(price);
        assertThatThrownBy(() -> productService.changePrice(productId, expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(createProduct("후라이드", BigDecimal.valueOf(16_000L)));
        productRepository.save(createProduct("양념치킨", BigDecimal.valueOf(16_000L)));
        assertThat(productService.findAll()).hasSize(2);
    }


    private Product createProduct(final String name, final BigDecimal price) {
        final ProductName productName = new ProductName(name, profanities);
        final ProductPrice productPrice = new ProductPrice(price);
        return new Product(productName, productPrice);
    }
}
