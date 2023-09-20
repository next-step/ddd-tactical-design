package kitchenpos.eatinorders.domain;

import static kitchenpos.eatinorders.domain.EatInOrderFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderLineItem;
import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderLineItems;
import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderQuantity;

class EatInOrderLineItemsTest {
    private EatInOrderLineItem 주문1;
    private EatInOrderLineItem 주문2;

    @BeforeEach
    void setUp() {
        EatInOrderQuantity quantity = EatInOrderQuantity.of(3);
        주문1 = new EatInOrderLineItem(createOrderMenu(13_000L), quantity);
        주문2 = new EatInOrderLineItem(createOrderMenu(23_000L), quantity);
    }

    @DisplayName("주문내역이 없으면 생성불가")
    @Test
    void order1() {
        assertThatThrownBy(() -> new EatInOrderLineItems(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문 내역이 없으면 등록할 수 없다.");
    }

    @DisplayName("주문내역에 있는 합계금액을 구한다.")
    @Test
    void order2() {
        EatInOrderLineItems orderLineItems = new EatInOrderLineItems(List.of(주문1, 주문2));
        assertThat(orderLineItems.sumOfOrderPrice())
            .isEqualTo(BigDecimal.valueOf(108_000));
    }
}
