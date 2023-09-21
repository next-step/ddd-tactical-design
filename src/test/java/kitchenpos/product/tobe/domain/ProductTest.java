package kitchenpos.product.tobe.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.math.BigDecimal;
import kitchenpos.Fixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

    @DisplayName("상품의 가격을 수정한다")
    @ParameterizedTest
    @ValueSource(longs = {0, 10_000L, 15_000L})
    void testChangePrice(long price) {
        // given
        Product product = Fixtures.product();
        var expectedPrice = BigDecimal.valueOf(price);

        // when
        product.changePrice(expectedPrice);

        // then
        assertThat(product.getPrice().getValue()).isEqualTo(expectedPrice);
    }

    @DisplayName("상품의 가격은 0 미만의 숫자로 변경할 수 없다")
    @ParameterizedTest
    @ValueSource(longs = {-1_000L, -10_000L})
    void testChangePriceWhenChangedPriceIsNotValid(Long price) {
        // given
        Product product = Fixtures.product();

        // when // then
        assertThatThrownBy(() -> product.changePrice(BigDecimal.valueOf(price)))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 null로 변경할 수 없다")
    @ParameterizedTest
    @NullSource
    void testChangePriceWhenChangedPriceIsNull(BigDecimal price) {
        // given
        Product product = Fixtures.product();

        // when // then
        assertThatThrownBy(() -> product.changePrice(price))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품이 N개 있을 때, 가격 합산을 구한다")
    @Test
    void testMultiplyPrice() {
        // given
        Product product = Fixtures.product(16_000L);

        // when
        var actual = product.multiplyPrice(2);

        // then
        assertThat(actual.getValue()).isEqualTo(BigDecimal.valueOf(32_000L));
    }
}
