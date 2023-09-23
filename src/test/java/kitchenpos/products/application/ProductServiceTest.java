package kitchenpos.products.application;

import kitchenpos.common.FakeProfanityPolicy;
import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.common.exception.PriceException;
import kitchenpos.products.ui.dto.ProductRequest;
import kitchenpos.products.ui.dto.ProductResponse;
import kitchenpos.products.exception.ProductDisplayedNameException;
import kitchenpos.products.tobe.domain.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.products.fixture.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("상품")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private kitchenpos.products.tobe.domain.ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        ProfanityPolicy profanityPolicy = new FakeProfanityPolicy();
        productService = new ProductService(productRepository, profanityPolicy);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final ProductRequest expected = new ProductRequest("후라이드", 16_000L);
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
        final ProductRequest expected = new ProductRequest("후라이드", price);
        assertThatThrownBy(() -> productService.create(expected))
                .isInstanceOf(PriceException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final ProductRequest expected = new ProductRequest(name, 16_000L);
        assertThatThrownBy(() -> productService.create(expected))
                .isInstanceOf(ProductDisplayedNameException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final ProductId productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final BigDecimal changePrice = BigDecimal.valueOf(15_000L);

        final ProductResponse actual = productService.changePrice(productId, changePrice);

        assertThat(actual.getPrice()).isEqualTo(changePrice);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final ProductId productId = productRepository.save(product("후라이드", 16_000L)).getId();
        assertThatThrownBy(() -> productService.changePrice(productId, price))
                .isInstanceOf(PriceException.class);
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(product("후라이드", 16_000L));
        productRepository.save(product("양념치킨", 16_000L));
        final List<ProductResponse> actual = productService.findAll();
        assertThat(actual).hasSize(2);
    }

}
