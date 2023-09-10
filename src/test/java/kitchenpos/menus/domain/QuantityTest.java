package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.menus.domain.tobe.domain.Quantity;

class QuantityTest {

    @DisplayName("수량은 0보다 작을 수 없다.")
    @Test
    void of() {
        assertThatThrownBy(() -> Quantity.of(-1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수량은 0보다 작을 수 없습니다.");
    }

    @DisplayName("수량이 변경된다.")
    @Test
    void changeQuantity() {
        Quantity quantity = Quantity.of(3);
        quantity = quantity.changeQuantity(5);
        assertThat(quantity.equals(Quantity.of(5))).isTrue();
    }
}
