package kitchenpos.eatinorders.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuantityTest {
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void 수량은_0개_이상이다(int quantity) {
        assertDoesNotThrow(() -> new Quantity(quantity));
    }

    @Test
    void 수량은_음수일_수_없다() {
        assertThatThrownBy(() -> new Quantity(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수량은 0개 이상이어야 합니다. 현재 값: -1");
    }

    @Test
    void Quantity_동등성_비교() {
        Quantity actual = new Quantity(0);

        assertThat(actual.equals(actual)).isTrue();

        assertThat(actual.equals(null)).isFalse();
        assertThat(actual.equals("wrong class")).isFalse();

        assertThat(actual.equals(new Quantity(0))).isTrue();
        assertThat(actual.equals(new Quantity(1))).isFalse();
    }

    @Test
    void Quantity_hashCode() {
        Quantity actual = new Quantity(0);

        assertThat(actual.hashCode()).isEqualTo(new Quantity(0).hashCode());
        assertThat(actual.hashCode()).isNotEqualTo(new Quantity(1).hashCode());
    }
}
