package kitchenpos.eatinorders.tobe.domain.common;

import kitchenpos.common.vo.Price;
import kitchenpos.menus.tobe.domain.MenuId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderLineItemTest {

    @DisplayName("한 개의 주문 항목의 총 액을 구한다")
    @Test
    void amount() {
        OrderLineItem orderLineItem = new OrderLineItem(1L, MenuId.generate(), 5, new Price(10_000));

        Price amount = orderLineItem.amount();

        assertThat(amount).isEqualTo(new Price(50_000));

    }

    @DisplayName("주문 항목에 동일한 가격의 메뉴가 있는지 확인한다")
    @Test
    void isSameMenuPrice() {
        MenuId id = MenuId.generate();
        OrderLineItem orderLineItem = new OrderLineItem(1L, id, 5, new Price(10_000));

        boolean result = orderLineItem.isSameMenuPrice(id, new Price(10_000));

        assertThat(result).isTrue();
    }
}
