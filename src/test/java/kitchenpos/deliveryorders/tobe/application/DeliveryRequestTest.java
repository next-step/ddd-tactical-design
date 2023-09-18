package kitchenpos.deliveryorders.tobe.application;

import kitchenpos.eatinorders.domain.OrderLineItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static kitchenpos.Fixtures.orderLineItem;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeliveryRequestTest {

    @DisplayName("메뉴가 없으면 등록할 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void validateOrderLineItems(List<OrderLineItem> orderLineItemList) {
        DeliveryRequest request = new DeliveryRequest(orderLineItemList, "주소");
        assertThatThrownBy(request::validate)
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 항목의 수량은 0 이상이어야 한다.")
    @Test
    void validateOrderLineItemQuantity() {
        OrderLineItem orderLineItem = orderLineItem();
        orderLineItem.setQuantity(-1L);
        DeliveryRequest request = new DeliveryRequest(List.of(orderLineItem), "주소");
        assertThatThrownBy(request::validate)
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void validateDeliveryAddress(String deliveryAddress) {
        final OrderLineItem orderLineItem = new OrderLineItem();
        DeliveryRequest request = new DeliveryRequest(List.of(orderLineItem), deliveryAddress);
        assertThatThrownBy(request::validate)
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
