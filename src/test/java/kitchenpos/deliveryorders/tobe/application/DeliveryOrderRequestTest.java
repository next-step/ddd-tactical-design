package kitchenpos.deliveryorders.tobe.application;

import kitchenpos.deliveryorders.tobe.application.dto.DeliveryOrderRequest;
import kitchenpos.deliveryorders.tobe.application.dto.OrderLineItemRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeliveryOrderRequestTest {

    @DisplayName("메뉴가 없으면 등록할 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void validateOrderLineItems(List<OrderLineItemRequest> orderLineItemList) {
        final DeliveryOrderRequest request = new DeliveryOrderRequest(orderLineItemList, "주소");
        assertThatThrownBy(request::validate)
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 항목의 수량은 0 이상이어야 한다.")
    @Test
    void validateOrderLineItemQuantity() {
        final var orderLineItem = orderLineItemRequest(-1L);
        DeliveryOrderRequest request = new DeliveryOrderRequest(List.of(orderLineItem), "주소");
        assertThatThrownBy(request::validate)
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void validateDeliveryAddress(String deliveryAddress) {
        final var orderLineItem = orderLineItemRequest(1L);
        DeliveryOrderRequest request = new DeliveryOrderRequest(List.of(orderLineItem), deliveryAddress);
        assertThatThrownBy(request::validate)
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    private OrderLineItemRequest orderLineItemRequest(final long quantity) {
        return new OrderLineItemRequest(
            quantity,
            UUID.randomUUID(),
            10_000L
        );
    }
}
