package kitchenpos.eatinorders.tobe.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderLineItemTest {
    @DisplayName("주문 상품을 생성한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> new OrderLineItem(1L, BigDecimal.valueOf(20000), 2));
    }

    @DisplayName("주문 상품의 개수를 변경한다.")
    @Test
    void changeQuantity() {
        OrderLineItem orderLineItem = new OrderLineItem(1L, BigDecimal.valueOf(20000), 2);

        orderLineItem.changeQuantity(5);

        Assertions.assertThat(orderLineItem.getQuantity()).isEqualTo(5);
    }
}
