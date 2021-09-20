package kitchenpos.common.tobe.domain;

import static kitchenpos.products.tobe.exception.WrongPriceException.PRICE_SHOULD_NOT_BE_NEGATIVE;
import static kitchenpos.products.tobe.exception.WrongPriceException.PRICE_SHOULD_NOT_BE_NULL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import kitchenpos.menus.tobe.domain.model.Quantity;
import kitchenpos.products.tobe.exception.WrongPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @DisplayName("상품의 가격이 null이면 예외가 발생한다")
    @ParameterizedTest
    @NullSource
    void priceNullException(final BigDecimal value) {
        assertThatThrownBy(
            () -> new Price(value)
        ).isInstanceOf(WrongPriceException.class)
            .hasMessage(PRICE_SHOULD_NOT_BE_NULL);
    }

    @DisplayName("상품의 가격이 0 미만이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(longs = {-1, -1000, Long.MIN_VALUE})
    void priceNegativeException(final long value) {
        assertThatThrownBy(
            () -> new Price(value)
        ).isInstanceOf(WrongPriceException.class)
            .hasMessage(PRICE_SHOULD_NOT_BE_NEGATIVE);
    }

    @DisplayName("상품의 가격이 같다면 equals의 결과도 같다")
    @ParameterizedTest
    @ValueSource(longs = {0, 1, 1000, Long.MAX_VALUE})
    void equals(final long value) {
        final Price price1 = new Price(value);
        final Price price2 = new Price(value);

        assertThat(price1.equals(price2)).isTrue();
    }

    @DisplayName("Quantity와 곱할 수 있다")
    @ParameterizedTest
    @CsvSource({"2,3,6", "5,5,25", "9,9,81"})
    void multiply(final long price, final long quantity, final long result) {
        final Price expect = new Price(price).multiply(new Quantity(quantity));

        assertThat(expect).isEqualTo(new Price(result));
    }

    @DisplayName("Price끼리 더할 수 있다")
    @ParameterizedTest
    @CsvSource({"2,3,5", "5,5,10", "9,9,18"})
    void add(final long price1, final long price2, final long result) {
        final Price expect = new Price(price1).add(new Price(price2));

        assertThat(expect).isEqualTo(new Price(result));
    }

}
