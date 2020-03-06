package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class PriceTest {

    @DisplayName("값이 0이상이면 price 생성")
    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "1000"})
    void validPrice(final long value) {
        // when
        final Price price = Price.of(BigDecimal.valueOf(value));

        // then
        assertThat(price).isNotNull();
    }

    @DisplayName("값이 null 이거나 0보다 작으면 예외 던짐")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"-1", "-2000"})
    void shouldThrowExceptionIfInvalidPrice(final BigDecimal value) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Price.of(value));
    }
}
