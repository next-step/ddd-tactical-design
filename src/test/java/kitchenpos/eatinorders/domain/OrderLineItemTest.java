package kitchenpos.eatinorders.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.eatinorders.domain.order.OrderLineItem;

class OrderLineItemTest {
    @Test
    void menuId는_없을_수_없다() {
        assertThatThrownBy(() -> new OrderLineItem(null, 1, 1000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴의 id는 null일 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void 수량은_0개_이상이다(int quantity) {
        assertDoesNotThrow(() -> new OrderLineItem(UUID.randomUUID(), quantity, 1000L));
    }

    @Test
    void 수량은_음수일_수_없다() {
        assertThatThrownBy(() -> new OrderLineItem(UUID.randomUUID(), -1, 1000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수량은 0개 이상이어야 합니다. 현재 값: -1");
    }
}