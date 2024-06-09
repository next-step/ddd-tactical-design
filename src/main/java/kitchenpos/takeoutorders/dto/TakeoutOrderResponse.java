package kitchenpos.takeoutorders.dto;

import kitchenpos.support.dto.OrderLineItemResponse;
import kitchenpos.takeoutorders.domain.TakeoutOrder;
import kitchenpos.takeoutorders.domain.TakeoutOrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TakeoutOrderResponse(
        UUID id, TakeoutOrderStatus status, LocalDateTime orderDateTime, List<OrderLineItemResponse> orderLineItems) {
    public static TakeoutOrderResponse from(TakeoutOrder request) {
        return new TakeoutOrderResponse(
                request.getId(),
                request.getStatus(),
                request.getOrderDateTime(),
                request.getOrderLineItems().stream()
                        .map(OrderLineItemResponse::from)
                        .toList()
        );
    }
}
