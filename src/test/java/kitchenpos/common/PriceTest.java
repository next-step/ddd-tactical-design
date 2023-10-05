package kitchenpos.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @DisplayName("가격을 생성한다")
    @ParameterizedTest
    @ValueSource(longs = {0L, 1_000L, 10_000L})
    void testInitPrice(long value) {
        // when // then
        assertDoesNotThrow(() -> Price.of(BigDecimal.valueOf(value)));
    }

    @DisplayName("유효하지 않은 값으로는 가격을 생성할 수 없다")
    @ParameterizedTest
    @MethodSource
    void testInitPriceIfValueIsNotValid(BigDecimal value) {
        // when // then
        assertThatThrownBy(() -> Price.of(value))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> testInitPriceIfValueIsNotValid() {
        return Arrays.asList(
            Arguments.of(BigDecimal.valueOf(-1L)),
            Arguments.of(BigDecimal.valueOf(-100L)),
            Arguments.of(BigDecimal.valueOf(-10_000L))
        );
    }

    @DisplayName("가격이 음수라면 가격 객체를 생성할 수 없다")
    @ParameterizedTest
    @ValueSource(longs = {-1_000, -10_000})
    void testInitPriceIfNotValidPrice(long value) {
        // when // then
        assertThatThrownBy(() -> new Price(BigDecimal.valueOf(value)))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격이 null이면 가격 객체를 생성할 수 없다")
    @Test
    void testInitPriceIfPriceIsNull() {
        // when // then
        assertThatThrownBy(() -> new Price(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격의 합산을 구한다")
    @Test
    void testAdd() {
        // given
        var sut = Price.of(BigDecimal.valueOf(10_000L));
        var operandPrice = Price.of(BigDecimal.valueOf(20_000L));

        // when
        var actual = sut.add(operandPrice);

        // then
        assertThat(actual).isEqualTo(Price.of(BigDecimal.valueOf(30_000L)));
    }

    @DisplayName("가격이 비교하는 가격보다 작거나 같은지 여부를 확인한다")
    @ParameterizedTest
    @MethodSource
    void testIsLowerThan(Price sut, Price comparisonGroup, boolean expected) {
        // when
        var actual = sut.isLowerThan(comparisonGroup);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private static List<Arguments> testIsLowerThan() {
        return List.of(
            Arguments.of(Price.of(BigDecimal.valueOf(10_000L)), Price.of(BigDecimal.valueOf(10_000L)), false),
            Arguments.of(Price.of(BigDecimal.valueOf(10_000L)), Price.of(BigDecimal.valueOf(10_001L)), true),
            Arguments.of(Price.of(BigDecimal.valueOf(10_000L)), Price.of(BigDecimal.valueOf(9_999L)), false)
        );
    }
}
