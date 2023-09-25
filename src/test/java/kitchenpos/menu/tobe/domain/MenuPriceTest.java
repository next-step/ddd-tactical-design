package kitchenpos.menu.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuPriceTest {

    @DisplayName("메뉴 가격을 생성한다")
    @ParameterizedTest
    @ValueSource(longs = {0L, 1_000L, 10_000L})
    void testInitMenuPrice(long value) {
        // when // then
        assertDoesNotThrow(() -> MenuPrice.of(BigDecimal.valueOf(value)));
    }

    @DisplayName("유효하지 않은 값으로는 메뉴 가격을 생성할 수 없다")
    @ParameterizedTest
    @MethodSource
    void testInitMenuPriceIfValueIsNotValid(BigDecimal value) {
        // when // then
        assertThatThrownBy(() -> MenuPrice.of(value))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> testInitMenuPriceIfValueIsNotValid() {
        return Arrays.asList(
            Arguments.of(BigDecimal.valueOf(-1L)),
            Arguments.of(BigDecimal.valueOf(-100L)),
            Arguments.of(BigDecimal.valueOf(-10_000L))
        );
    }

    @DisplayName("메뉴 가격의 합산을 구한다")
    @Test
    void testAdd() {
        // given
        var sut = MenuPrice.of(BigDecimal.valueOf(10_000L));
        var operandMenuPrice = MenuPrice.of(BigDecimal.valueOf(20_000L));

        // when
        var actual = sut.add(operandMenuPrice);

        // then
        assertThat(actual).isEqualTo(MenuPrice.of(BigDecimal.valueOf(30_000L)));
    }

    @DisplayName("메뉴 가격이 비교하는 메뉴 가격보다 작거나 같은지 여부를 확인한다")
    @ParameterizedTest
    @MethodSource
    void testIsLowerThan(MenuPrice sut, MenuPrice comparisonGroup, boolean expected) {
        // when
        var actual = sut.isLowerThan(comparisonGroup);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private static List<Arguments> testIsLowerThan() {
        return List.of(
            Arguments.of(MenuPrice.of(BigDecimal.valueOf(10_000L)), MenuPrice.of(BigDecimal.valueOf(10_000L)), false),
            Arguments.of(MenuPrice.of(BigDecimal.valueOf(10_000L)), MenuPrice.of(BigDecimal.valueOf(10_001L)), true),
            Arguments.of(MenuPrice.of(BigDecimal.valueOf(10_000L)), MenuPrice.of(BigDecimal.valueOf(9_999L)), false)
        );
    }
}
