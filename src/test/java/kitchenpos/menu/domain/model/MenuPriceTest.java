package kitchenpos.menu.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuPriceTest {

    @DisplayName("가격은 비어있거나 null이면 예외가 발생한다")
    @ParameterizedTest
    @MethodSource("invalidMenuPriceProvider")
    void create_price_exception(BigDecimal value) {
        assertThatThrownBy(() -> new MenuPrice(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 가격을 채워주세요!");
    }

    private static Stream<BigDecimal> invalidMenuPriceProvider() {
        return Stream.of(
                null,
                BigDecimal.valueOf(-1)
        );
    }
}
