package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MenuPriceTest {

    @DisplayName("MenuPrice를 생성할 수 있다.")
    @Test
    void create() {
        final MenuPrice menuPrice = MenuPrice.from(BigDecimal.valueOf(10000L));
        assertAll(
                () -> assertNotNull(menuPrice),
                () -> assertEquals(BigDecimal.valueOf(10000L), menuPrice.getValue())
        );
    }

    @DisplayName("MenuPrice 생성 시 값이 null이면 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void createWithNullValue(BigDecimal value) {
        assertThatThrownBy(() -> MenuPrice.from(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("MenuPrice 생성 시 값이 음수면 예외를 던진다.")
    @Test
    void createWithNegativeValue() {
        assertThatThrownBy(() -> MenuPrice.from(BigDecimal.valueOf(-1L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("MenuPrice 끼리 더할 수 있다.")
    @Test
    void add() {
        final MenuPrice menuPrice = MenuPrice.from(BigDecimal.valueOf(10000L));
        final MenuPrice other = MenuPrice.from(BigDecimal.valueOf(20000L));
        final MenuPrice actual = menuPrice.add(other);
        assertThat(actual).isEqualTo(MenuPrice.from(BigDecimal.valueOf(30000L)));
    }

    @DisplayName("MenuPrice에 수량을 곱할 수 있다.")
    @Test
    void multiplyByQuantity() {
        final MenuPrice menuPrice = MenuPrice.from(BigDecimal.valueOf(10000L));
        final MenuPrice actual = menuPrice.multiplyByQuantity(2L);
        assertThat(actual).isEqualTo(MenuPrice.from(BigDecimal.valueOf(20000L)));
    }

    @DisplayName("MenuPrice가 다른 MenuPrice보다 크면 true를 반환한다.")
    @Test
    void isBiggerThan() {
        final MenuPrice menuPrice = MenuPrice.from(BigDecimal.valueOf(10001L));
        final MenuPrice other = MenuPrice.from(BigDecimal.valueOf(10000L));
        assertThat(menuPrice.isBiggerThan(other)).isTrue();
    }

    @DisplayName("MenuPrice가 다른 MenuPrice보다 작으면 false를 반환한다.")
    @Test
    void isBiggerThanWhenMenuPriceIsLessThanOther() {
        final MenuPrice menuPrice = MenuPrice.from(BigDecimal.valueOf(10000L));
        final MenuPrice other = MenuPrice.from(BigDecimal.valueOf(10001L));
        assertThat(menuPrice.isBiggerThan(other)).isFalse();
    }
}
