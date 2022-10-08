package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import kitchenpos.menus.exception.InvalidMenuProductPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuProductPriceTest {

    @DisplayName("메뉴의 상품 가격은 Null 일 수 없다.")
    @Test
    void nullException() {
        assertThatThrownBy(() -> new MenuProductPrice(null))
            .isExactlyInstanceOf(InvalidMenuProductPriceException.class);
    }

    @DisplayName("메뉴의 상품의 가격은 0보다 작을 수 없다.")
    @ValueSource(strings = {"-1", "-10"})
    @ParameterizedTest
    void negativeException(BigDecimal negativePrice) {
        assertThatThrownBy(() -> new MenuProductPrice(negativePrice))
            .isExactlyInstanceOf(InvalidMenuProductPriceException.class);
    }
}
