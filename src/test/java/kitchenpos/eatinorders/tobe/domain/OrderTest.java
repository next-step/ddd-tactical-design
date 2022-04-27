package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static kitchenpos.TobeFixtures.newOrderLineItem;
import static kitchenpos.TobeFixtures.newOrderLineItems;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class OrderTest {

    @Test
    @DisplayName("배달 주문 생성 시 주문 메뉴에 주문 수가 0개 미만인 메뉴가 포함되면 생성 실패")
    void createDeliveryOrderFail01() {
        // given
        OrderLineItem item1 = newOrderLineItem("name", 1000L, 1L);
        OrderLineItem item2 = newOrderLineItem("name", 1000L, -1L);
        OrderLineItems orderLineItems = newOrderLineItems(item1, item2);

        // when
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Order.createDeliveryOrder(orderLineItems, null))
            .withMessageContaining("최소 0개");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("배달 주문 생성 시 배달 주소가 비어 있으면 생성 실패")
    void createDeliveryOrderFail02(String deliveryAddress) {
        // given
        OrderLineItems orderLineItems = newOrderLineItems(newOrderLineItem("name", 1000L, 1L));

        // when
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Order.createDeliveryOrder(orderLineItems, deliveryAddress))
            .withMessageContaining("배달주소");
    }

    @Test
    @DisplayName("배달 주문 생성 시 대기 상태 및 배달 유형 주문으로 생성")
    void createDeliveryOrderSuccess() {
        // given
        OrderLineItems orderLineItems = newOrderLineItems(newOrderLineItem("name", 1000L, 1L));

        // when
        Order actual = Order.createDeliveryOrder(orderLineItems, "주소");

        // then
        assertThat(actual.getType()).isEqualTo(OrderType.DELIVERY);
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING);
        assertThat(actual.getDeliveryAddress()).isNotBlank();
    }
}
