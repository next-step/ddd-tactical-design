package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import kitchenpos.eatinorders.TobeFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderLineItemsTest {
    @DisplayName("금액의 총합을 반환")
    @Test
    void totalAmount() {
        // given
        OrderLineItems orderLineItems = new OrderLineItems(
            TobeFixtures.orderLineItem(),
            TobeFixtures.orderLineItem()
        );

        // when
        BigDecimal totalAmount = orderLineItems.getTotalAmount();

        // then
        assertThat(totalAmount).isEqualTo(
            new BigDecimal(TobeFixtures.ORDER_LINE_ITEM_QUANTITY * TobeFixtures.ORDER_LINE_ITEM_PRICE * 2)
        );
    }

    @DisplayName("숨겨진 주문 항목이 하나라도 있는지 여부 반환")
    @Test
    void isAnyNotDisplayed() {
        // given
        OrderLineItems orderLineItems = new OrderLineItems(
            TobeFixtures.orderLineItem(false),
            TobeFixtures.orderLineItem(true)
        );

        // when
        boolean result = orderLineItems.isAnyNotDisplayed();

        // then
        assertThat(result).isTrue();
    }
}
