package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.infra.InMemoryProductDao;
import kitchenpos.products.tobe.infra.ProductDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceTest {
    private final ProductDao productDao = new InMemoryProductDao();

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productDao);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        // given
        final Product expected = ProductBuilder.product()
                .withFriedChicken()
                .build();

        // when
        final Product actual = productService.create(
                expected.getName(),
                expected.getPrice().toBigDecimal());

        // then
        assertProduct(expected, actual);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "-1000")
    void create(final BigDecimal price) {
        // given
        final String givenProductName = "후라이드";
        final BigDecimal givenPrice = price;

        // when
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> productService.create(
                        givenProductName,
                        givenPrice
                ));
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void list() {
        //given
        final Product friedChicken = productDao.save(ProductBuilder
                .product()
                .withFriedChicken()
                .build());
        final Product seasonedChicken = productDao.save(ProductBuilder
                .product()
                .withSeasonedChicken()
                .build());

        //when
        final List<Product> actual = productService.list();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(
                Arrays.asList(
                        friedChicken,
                        seasonedChicken
                )
        );
    }

    private void assertProduct(final Product expected, final Product actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice().toBigDecimal()).isEqualTo(expected.getPrice().toBigDecimal())
        );
    }
}