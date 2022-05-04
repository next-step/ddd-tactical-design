package kitchenpos.eatinorders.tobe.domain;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class QuantityTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("재고가 1개 이상이면 true, 아니면 false 반환")
    void hasMoreThanOneTest(long value, boolean expected) {
        Quantity quantity = new Quantity(value);
        assertThat(quantity.hasMoreThanOne()).isEqualTo(expected);
    }

    public static Stream<Arguments> hasMoreThanOneTest() {
        return Stream.of(
            Arguments.of(1L, true),
            Arguments.of(100L, true),
            Arguments.of(0L, false),
            Arguments.of(-5L, false)
        );
    }
}
