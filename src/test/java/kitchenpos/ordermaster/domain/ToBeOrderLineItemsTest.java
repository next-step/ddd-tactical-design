package kitchenpos.ordermaster.domain;

import static kitchenpos.ordermaster.domain.OrderFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.eatinorders.domain.OrderType;

class ToBeOrderLineItemsTest {
    private ToBeOrderLineItem 주문1;
    private ToBeOrderLineItem 주문2;

    @BeforeEach
    void setUp() {
        OrderQuantity quantity = OrderQuantity.of(3, OrderType.DELIVERY);
        주문1 = new ToBeOrderLineItem(createOrderMenu(13_000L), quantity);
        주문2 = new ToBeOrderLineItem(createOrderMenu(23_000L), quantity);
    }

    @DisplayName("주문내역이 없으면 생성불가")
    @Test
    void order1() {
        assertThatThrownBy(() -> new ToBeOrderLineItems(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문 내역이 없으면 등록할 수 없다.");
    }

    @DisplayName("주문내역에 있는 합계금액을 구한다.")
    @Test
    void order2() {
        ToBeOrderLineItems orderLineItems = new ToBeOrderLineItems(List.of(주문1, 주문2));
        assertThat(orderLineItems.sumOfOrderPrice())
            .isEqualTo(BigDecimal.valueOf(108_000));
    }
}
