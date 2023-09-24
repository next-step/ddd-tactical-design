package kitchenpos.menus.tobe.domain.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @DisplayName("Menu의 Price를 생성할 수 있다.")
    @Test
    void create() {
        final Price price = Price.from(BigDecimal.valueOf(10000L));
        assertAll(
                () -> assertNotNull(price),
                () -> assertEquals(BigDecimal.valueOf(10000L), price.getValue())
        );
    }

    @DisplayName("Menu의 Price 생성 시 값이 null이면 예외를 던진다.")
    @ParameterizedTest
    @NullSource
    void createWithNullValue(BigDecimal value) {
        assertThatThrownBy(() -> Price.from(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Menu의 Price 생성 시 값이 0미만의 음수면 예외를 던진다.")
    @Test
    void createWithNegativeValue() {
        assertThatThrownBy(() -> Price.from(BigDecimal.valueOf(-1L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Menu의 Price 끼리 더할 수 있다.")
    @Test
    void add() {
        final Price price = Price.from(BigDecimal.valueOf(10000L));
        final Price other = Price.from(BigDecimal.valueOf(20000L));
        final Price actual = price.add(other);
        assertThat(actual).isEqualTo(Price.from(BigDecimal.valueOf(30000L)));
    }

    @DisplayName("Menu의 Price에 수량을 곱할 수 있다.")
    @Test
    void multiplyByQuantity() {
        final Price price = Price.from(BigDecimal.valueOf(10000L));
        final Price actual = price.multiplyByQuantity(2L);
        assertThat(actual).isEqualTo(Price.from(BigDecimal.valueOf(20000L)));
    }

    @DisplayName("Menu의 Price가 다른 Menu의 Price와 비교하여 true를 반환한다.")
    @Test
    void isBiggerThan() {
        final Price price = Price.from(BigDecimal.valueOf(10001L));
        final Price other = Price.from(BigDecimal.valueOf(10000L));
        assertThat(price.isBiggerThan(other)).isTrue();
    }

    @DisplayName("Menu의 Price가 다른 Menu의 Price와 비교하여 작으면 false를 반환한다.")
    @Test
    void isBiggerThanWhenMenuPriceIsLessThanOther() {
        final Price price = Price.from(BigDecimal.valueOf(10000L));
        final Price other = Price.from(BigDecimal.valueOf(10001L));
        assertThat(price.isBiggerThan(other)).isFalse();
    }
}
