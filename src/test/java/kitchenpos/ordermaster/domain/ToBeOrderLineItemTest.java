package kitchenpos.ordermaster.domain;

import static kitchenpos.ordermaster.domain.OrderFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.eatinorders.domain.OrderType;

class ToBeOrderLineItemTest {
    private ToBeOrderLineItem 주문1;

    @BeforeEach
    void setUp() {
        OrderQuantity quantity = OrderQuantity.of(3, OrderType.DELIVERY);
        주문1 = new ToBeOrderLineItem(createOrderMenu(13_000L), quantity);
    }

    @DisplayName("메뉴 가격을 조회한다. ( 메뉴가격 * 수량 )")
    @Test
    void name() {
        assertThat(주문1.menuPrice()).isEqualTo(OrderMenuPrice.of(39_000));
    }
}
