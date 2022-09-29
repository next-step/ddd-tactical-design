package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import kitchenpos.eatinorders.TobeFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderLineItemTest {
    @DisplayName("가격과 수량을 곱하여 금액을 반환")
    @Test
    void getAmount() {
        // given
        OrderLineItem orderLineItem = TobeFixtures.orderLineItem();

        // when
        BigDecimal amount = orderLineItem.getAmount();

        // then
        assertThat(amount).isEqualTo(
            new BigDecimal(TobeFixtures.ORDER_LINE_ITEM_PRICE * TobeFixtures.ORDER_LINE_ITEM_QUANTITY)
        );
    }
}
