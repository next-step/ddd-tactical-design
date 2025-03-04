package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.vo.Price;
import kitchenpos.eatinorders.tobe.domain.common.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.common.OrderLineItems;
import kitchenpos.menus.tobe.domain.MenuId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EatInOrderLineItemsTest {

    @DisplayName("주문 항목의 수량이 음수가 있는지 확인한다")
    @Test
    void hasNegativeQuantity() {
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, MenuId.generate(), -1, new Price(10_000)),
                new OrderLineItem(2L, MenuId.generate(), 1, new Price(20_000))
        );

        assertThatThrownBy(() -> new EatInOrderLineItems(orderLineItems));
    }
}
