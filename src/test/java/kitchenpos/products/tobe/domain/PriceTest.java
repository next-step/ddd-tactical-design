package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @DisplayName("가격이 음수일경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"-1000", "-2000", "-10000", "-18000"})
    void given_negative_price_when_new_price_then_throw_exception(BigDecimal price) {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Price.of(price));
    }

    @DisplayName("가격이 음수일경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(
        value = {
            "3000,2,6000",
            "18000,2,36000"
        }
    )
    void multiplyTest(BigDecimal origin, BigDecimal multiply, BigDecimal expect) {
        Price price = Price.of(origin).multiply(multiply);

        assertThat(price).isEqualTo(Price.of(expect));
    }
}
