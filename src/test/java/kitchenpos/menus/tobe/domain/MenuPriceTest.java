package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MenuPriceTest {
    @DisplayName("상품 가격은 비어있을 수 없다.")
    @Test
    void emptyPrice() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MenuPrice(null))
                .withMessage("메뉴 가격은 필수값입니다.");
    }

    @DisplayName("상품 가격은 음수 일 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    void negativePrice(final int price) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MenuPrice(BigDecimal.valueOf(price)))
                .withMessage("메뉴 가격은 음수가 될 수 없습니다.");
    }
}
