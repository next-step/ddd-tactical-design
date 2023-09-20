package kitchenpos.deliveryorders.domain;

import static kitchenpos.deliveryorders.domain.DeliveryOrderFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeliveryOrderLineItemTest {

    private DeliveryOrderLineItem 주문1;

    @BeforeEach
    void setUp() {
        DeliveryOrderQuantity quantity = DeliveryOrderQuantity.of(3);
        주문1 = new DeliveryOrderLineItem(createOrderMenu(12_000), quantity);
    }

    @DisplayName("메뉴 가격을 조회한다. ( 메뉴가격 * 수량 )")
    @Test
    void name() {
        assertThat(주문1.menuPrice()).isEqualTo(DeliveryOrderMenuPrice.of(36_000));
    }
}
