package kitchenpos.deliveryorders.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeliveryOrderLineItemQuantityTest {

    @DisplayName("배달 주문의 주문항목 수량은 0 이상이야 합니다.")
    @Test
    void deliveryOrderLineItemQuantity_should_be_greater_than_zero() {
        Assertions.assertThatThrownBy(() -> new DeliveryOrderLineItemQuantity(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("배달 주문의 주문 항목 개수는 0이상이어야 합니다.");
    }
}