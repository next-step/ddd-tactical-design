package kitchenpos.products.tobe.domain;

import kitchenpos.menus.tobe.domain.Quantity;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class PriceTest {

    @ParameterizedTest
    @ValueSource(strings = "7000")
    @DisplayName("상품의 가격을 생성할 수 있다.")
    void create(final BigDecimal value) {
        final Price price = new Price(value);

        assertThat(price).isEqualTo(new Price(value));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("상품의 가격이 비어있으면 IllegalArgumentException이 발생한다.")
    void create_empty(final BigDecimal value) {
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new Price(value);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(throwingCallable);
    }

    @ParameterizedTest
    @ValueSource(strings = "-1000")
    @DisplayName("상품의 가격이 0원 미만이면 IllegalArgumentException이 발생한다.")
    void create_negative(final BigDecimal value) {
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new Price(value);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(throwingCallable);
    }

    @Test
    @DisplayName("가격에 수량을 곱하여 계산할 수 있다.")
    void calculate() {
        final BigDecimal priceValue = BigDecimal.valueOf(1000L);
        final long quantityValue = 2L;
        final Price price = new Price(priceValue);
        final Quantity quantity = new Quantity(quantityValue);

        final BigDecimal expected = price.calculate(quantity);

        assertThat(expected).isEqualTo(priceValue.multiply(BigDecimal.valueOf(quantityValue)));
    }
}
