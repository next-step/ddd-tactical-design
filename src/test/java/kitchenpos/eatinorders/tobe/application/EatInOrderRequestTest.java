package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.application.dto.EatInOrderRequest;
import kitchenpos.eatinorders.tobe.application.dto.OrderLineItemRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EatInOrderRequestTest {

    @DisplayName("메뉴가 없으면 등록할 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void validateOrderLineItems(List<OrderLineItemRequest> orderLineItemList) {
        final EatInOrderRequest request = new EatInOrderRequest(orderLineItemList, UUID.randomUUID());
        assertThatThrownBy(request::validate)
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 테이블 ID가 빈 값일 수 없다")
    @Test
    void validateDeliveryAddress() {
        final var orderLineItem = orderLineItemRequest(1L);
        EatInOrderRequest request = new EatInOrderRequest(List.of(orderLineItem), null);
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
