package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @DisplayName("상품의 가격이 0 미만이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(longs = {-1, -1000, Long.MIN_VALUE})
    void exception(final long value) {
        assertThatThrownBy(
            () -> Price.of(value)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격이 같다면 equals의 결과도 같다")
    @ParameterizedTest
    @ValueSource(longs = {0, 1, 1000, Long.MAX_VALUE})
    void equals(final long value) {
        final Price price1 = Price.of(value);
        final Price price2 = Price.of(value);

        assertThat(price1.equals(price2)).isTrue();
    }

}
