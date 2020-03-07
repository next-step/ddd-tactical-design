package kitchenpos.products.service;

import kitchenpos.products.dto.ProductRequest;
import kitchenpos.products.dto.ProductResponse;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.products.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceTest {
    private final ProductRepository productRepository = new InMemoryProductRepository();

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        // given
        final ProductRequest expected = friedChickenRequest();

        // when
        final ProductResponse actual = productService.create(expected);

        // then
        assertProduct(expected, actual);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "-1000")
    void create(final BigDecimal price) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> productService.create(productWithPriceRequest(price)));
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final Product friedChicken = productRepository.save(friedChicken());
        final Product seasonedChicken = productRepository.save(seasonedChicken());

        // when
        final List<ProductResponse> actual = productService.list();

        // then
        assertThat(actual.get(0)).isEqualToComparingOnlyGivenFields(friedChicken);
        assertThat(actual.get(1)).isEqualToComparingOnlyGivenFields(seasonedChicken);
    }

    private void assertProduct(final ProductRequest expected, final ProductResponse actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }
}
