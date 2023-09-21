package kitchenpos.eatinorders.domain;

import static kitchenpos.eatinorders.domain.EatInOrderFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderLineItem;
import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderMenuPrice;
import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderQuantity;

class EatInOrderLineItemTest {
    private EatInOrderLineItem 주문1;

    @BeforeEach
    void setUp() {
        EatInOrderQuantity quantity = EatInOrderQuantity.of(3);
        주문1 = new EatInOrderLineItem(createOrderMenu(13_000L), quantity);
    }

    @DisplayName("메뉴 가격을 조회한다. ( 메뉴가격 * 수량 )")
    @Test
    void name() {
        assertThat(주문1.menuPrice()).isEqualTo(EatInOrderMenuPrice.of(39_000));
    }
}
