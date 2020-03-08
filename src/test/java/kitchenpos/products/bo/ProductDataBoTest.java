package kitchenpos.products.bo;

import kitchenpos.products.dao.ProductDao;
import kitchenpos.products.model.ProductData;
import kitchenpos.products.tobe.application.ProductApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static kitchenpos.products.Fixtures.friedChicken;
import static kitchenpos.products.Fixtures.seasonedChicken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductDataBoTest {
    private final ProductDao productDao = new InMemoryProductDao();
    private ProductApplication productApplication ;
    private ProductBo productBo;

    @BeforeEach
    void setUp() {
        productApplication = new ProductApplication(productDao);
        productBo = new ProductBo(productApplication);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        // given
        final ProductData expected = friedChicken();

        // when
        final ProductData actual = productBo.create(expected);

        // then
        assertProduct(expected, actual);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "-1000")
    void create(final BigDecimal price) {
        // given
        final ProductData expected = friedChicken();
        expected.setPrice(price);

        // when
        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> productBo.create(expected));
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final ProductData friedChicken = productDao.save(friedChicken());
        final ProductData seasonedChicken = productDao.save(seasonedChicken());

        // when
        final List<ProductData> actual = productBo.list();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(Arrays.asList(friedChicken, seasonedChicken));
    }

    private void assertProduct(final ProductData expected, final ProductData actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }
}
