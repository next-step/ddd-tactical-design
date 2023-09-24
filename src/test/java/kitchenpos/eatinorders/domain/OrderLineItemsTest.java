package kitchenpos.eatinorders.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class OrderLineItemsTest {
    @Test
    void OrderLineItem은_1개_이상이어야_한다() {
        assertDoesNotThrow(() -> new OrderLineItems(List.of(new OrderLineItem(UUID.randomUUID(), 1, 1000L))));
    }
    @Test
    void OrderLineItem은_없거나_비어있을_수_없다() {
        assertThatThrownBy(() -> new OrderLineItems(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 항목은 1개 이상이어야 합니다. 현재 값: null");
        assertThatThrownBy(() -> new OrderLineItems(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 항목은 1개 이상이어야 합니다. 현재 값: 0");
    }
}
