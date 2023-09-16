package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderLineItemQuantityTest {

    @DisplayName("주문내역 수량은 동등성이 보장되어야 한다")
    @Test
    void test1() {
        //given
        OrderLineItemQuantity quantity1 = new OrderLineItemQuantity(1);
        OrderLineItemQuantity quantity2 = new OrderLineItemQuantity(1);

        //then
        assertThat(quantity1).isEqualTo(quantity2);
    }
}