package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuPriceTest {

    @DisplayName("메뉴 가격을 생성할 수 있다.")
    @Test
    void constructor() {
        assertDoesNotThrow(() -> new MenuPrice(BigDecimal.valueOf(10000)));
    }

    @DisplayName("메뉴의 가격은 0원 보다 작을 수 없다.")
    @Test
    void negativePrice() {
        assertThatThrownBy(() -> new MenuPrice(BigDecimal.valueOf(-1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 메뉴 가격 입니다.");
    }

    @DisplayName("메뉴의 가격은 존재하지 않을 수 없다.")
    @ParameterizedTest
    @NullSource
    void nullPrice(BigDecimal price) {
        assertThatThrownBy(() -> new MenuPrice(price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 메뉴 가격 입니다.");
    }
}
