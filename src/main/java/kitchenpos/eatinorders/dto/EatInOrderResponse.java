package kitchenpos.eatinorders.dto;

import kitchenpos.eatinorders.todo.domain.orders.EatInOrder;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderStatus;
import kitchenpos.support.dto.OrderLineItemResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EatInOrderResponse(
        UUID id, EatInOrderStatus status, LocalDateTime orderDateTime, UUID orderTableId,
        List<OrderLineItemResponse> orderLineItems) {
    public static EatInOrderResponse from(EatInOrder request) {
        return new EatInOrderResponse(
                request.getId(),
                request.getStatus(),
                request.getOrderDateTime(),
                request.getOrderTableId(),
                request.getOrderLineItems().stream()
                        .map(OrderLineItemResponse::from)
                        .toList()
        );
    }
}
