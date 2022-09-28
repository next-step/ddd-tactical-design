package kitchenpos.global.vo;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuantityTest {

    @DisplayName("수량을 생성할 수 있다.")
    @Test
    void create() {
        Quantity actual = quantity(0);

        assertThat(actual).isEqualTo(quantity(0));
        assertThat(actual.hashCode() == quantity(0).hashCode())
                .isTrue();
    }

    private Quantity quantity(long quantity) {
        return new Quantity(quantity);
    }

    @DisplayName("수량은 0개 이상이어야 한다.")
    @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
    @ValueSource(longs = -1)
    void error1(long actual) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> quantity(actual));
    }
}
