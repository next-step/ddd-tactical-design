package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.TobeFixtures.newOrderLineItem;
import static kitchenpos.TobeFixtures.newOrderLineItems;
import static org.assertj.core.api.Assertions.assertThat;

class OrderLineItemsTest {

    @Test
    @DisplayName("주문 메뉴 가격의 합은 메뉴 가격과 재고를 곱한 수의 합을 반환")
    void sumOfMenuPricesTest() {
        // given
        OrderLineItem item1 = newOrderLineItem("name", 1000L, 1L);
        OrderLineItem item2 = newOrderLineItem("name", 2000L, 2L);
        OrderLineItem item3 = newOrderLineItem("name", 3000L, 3L);

        OrderLineItems orderLineItems = newOrderLineItems(item1, item2, item3);

        // when
        long actual = orderLineItems.sumOfMenuPrices();

        // then
        assertThat(actual).isEqualTo(item1.getOrderLinePrice() + item2.getOrderLinePrice() + item3.getOrderLinePrice());
    }
}
