package kitchenpos.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuantityTest {

    @DisplayName("수량을 생성할 수 있다.")
    @ValueSource(strings = {"0", "1"})
    @ParameterizedTest
    void 생성(final Long quantity) {
        assertDoesNotThrow(
            () -> new Quantity(quantity)
        );
    }

    @DisplayName("수량은 음수(-)일 수 없다.")
    @ValueSource(strings = {"-1"})
    @ParameterizedTest
    void 마이너스수량(final long quantity) {
        assertThatThrownBy(
            () -> new Quantity(quantity)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("수량 간 동등성을 확인할 수 있다.")
    @Test
    void 동등성() {
        final Quantity quantity1 = new Quantity(1L);
        final Quantity quantity2 = new Quantity(1L);

        assertThat(quantity1).isEqualTo(quantity2);
    }
}
