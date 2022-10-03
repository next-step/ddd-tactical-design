package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import kitchenpos.menus.exception.InvalidMenuPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuPriceTest {

    @DisplayName("메뉴 가격은 Null 일 수 없다.")
    @Test
    void nullException() {
        assertThatThrownBy(() -> new MenuPrice(null))
            .isExactlyInstanceOf(InvalidMenuPriceException.class);
    }

    @DisplayName("메뉴의 가격은 0보다 작을 수 없다.")
    @ValueSource(strings = {"-1", "-10"})
    @ParameterizedTest
    void negativeException(BigDecimal negativePrice) {
        assertThatThrownBy(() -> new MenuPrice(negativePrice))
            .isExactlyInstanceOf(InvalidMenuPriceException.class);
    }
}
