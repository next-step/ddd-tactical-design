package kitchenpos.products.tobe.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class PriceTest {

    @ParameterizedTest
    @ValueSource(strings = "1000")
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

}