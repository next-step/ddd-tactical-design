package kitchenpos.common.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class PriceTest {

    @DisplayName("가격은 비어있거나 null이면 예외가 발생한다")
    @ParameterizedTest
    @MethodSource("invalidPriceProvider")
    void create_price_exception(BigDecimal value) {
        assertThatThrownBy(() -> new Price(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격을 채워주세요!");
    }

    private static Stream<BigDecimal> invalidPriceProvider() {
        return Stream.of(
                null,
                BigDecimal.valueOf(-1)
        );
    }
}
