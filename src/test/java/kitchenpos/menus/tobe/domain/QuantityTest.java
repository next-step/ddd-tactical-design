package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    @DisplayName("수량을 생설 할 수 있다.")
    @ValueSource(strings = {"0", "10"})
    @ParameterizedTest
    void createPositiveValue(final long value) {
        Quantity actual = new Quantity(value);

        assertThat(actual.getQuantity()).isEqualTo(value);
    }

    @DisplayName("수량은 0이상이어야 한다.")
    @ValueSource(strings = {"-1", "-10"})
    @ParameterizedTest
    void createNegativeValue(final long value) {
        assertThatThrownBy(() -> new Quantity(value))
            .isInstanceOf(IllegalArgumentException.class);
    }

}
