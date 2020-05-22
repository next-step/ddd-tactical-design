package kitchenpos.products.tobe.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PriceTest {

    @DisplayName("가격이 음수라면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"-2", "-1"})
    void negativePriceTest(final long number) {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> Price.of(BigDecimal.valueOf(number)));
    }

    @DisplayName("가격이 null이면 예외 발생")
    @ParameterizedTest
    @NullSource
    void nullPriceTest(final BigDecimal price) {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> Price.of(price));
    }

    @DisplayName("생성 성공")
    @ParameterizedTest
    @ValueSource(strings = {"1000", "20000"})
    void createTest(final long number) {
        Price result = Price.of(BigDecimal.valueOf(number));
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(number));
    }
}
