package kitchenpos.products.bo;

import static kitchenpos.products.Fixtures.friedChickenRequest;
import static kitchenpos.products.Fixtures.seasonedChickenRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import kitchenpos.products.model.ProductRequest;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductBoTest {

    private ProductBo productBo;

    @BeforeEach
    void setUp() {
        productBo = new ProductBo(new InMemoryProductRepository());
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        // given
        final ProductRequest expected = friedChickenRequest();

        // when
        final Product actual = productBo.create(expected);

        // then
        assertProduct(expected, actual);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "-1000")
    void create(final BigDecimal price) {
        // given
        final ProductRequest expected = seasonedChickenRequest();
        expected.setPrice(price);

        // when
        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> productBo.create(expected));
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final Product friedChicken = productBo.create(friedChickenRequest());
        final Product seasonedChicken = productBo.create(seasonedChickenRequest());

        // when
        final List<Product> actual = productBo.list();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(Arrays.asList(friedChicken, seasonedChicken));
    }

    private void assertProduct(final ProductRequest expected, final Product actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice().getValue()).isEqualTo(expected.getPrice())
        );
    }
}
