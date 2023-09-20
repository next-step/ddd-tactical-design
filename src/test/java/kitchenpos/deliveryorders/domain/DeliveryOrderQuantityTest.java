package kitchenpos.deliveryorders.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeliveryOrderQuantityTest {

    @DisplayName("수량은 0보다 작을 수 없다.")
    @Test
    void of() {
        assertThatThrownBy(() -> DeliveryOrderQuantity.of(-1L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수량은 0보다 작을 수 없습니다.");
    }
}
