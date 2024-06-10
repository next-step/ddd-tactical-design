package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class OrderLineItemTest {

    @Test
    @DisplayName("개수는 0개 이상이어야 한다.")
    void canNotHaveMinusQuantity() {
        assertThatThrownBy(() -> new OrderLineItem(UUID.randomUUID(), -1, 10_000))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("수량이 음수이하일 수 없습니다.");
    }
}
