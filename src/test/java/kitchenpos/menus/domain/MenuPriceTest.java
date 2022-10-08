package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.assertThat;
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

    @DisplayName("주어진 값보다 메뉴 가격이 큰지 비교할 수 있다.")
    @Test
    void isBiggerThan() {
        MenuPrice menuPrice = new MenuPrice(BigDecimal.valueOf(3));

        assertThat(menuPrice.isBiggerThan(2)).isTrue();
        assertThat(menuPrice.isBiggerThan(3)).isFalse();
        assertThat(menuPrice.isBiggerThan(4)).isFalse();
    }
}
