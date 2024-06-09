package kitchenpos.deliveryorders.dto;

import kitchenpos.deliveryorders.domain.DeliveryOrder;
import kitchenpos.deliveryorders.domain.DeliveryOrderStatus;
import kitchenpos.support.dto.OrderLineItemResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record DeliveryOrderResponse(
        UUID id, DeliveryOrderStatus status,
        LocalDateTime orderDateTime,
        String deliveryAddress,
        List<OrderLineItemResponse> orderLineItems
) {
    public static DeliveryOrderResponse from(DeliveryOrder request) {
        return new DeliveryOrderResponse(
                request.getId(),
                request.getStatus(),
                request.getOrderDateTime(),
                request.getDeliveryAddress(),
                request.getOrderLineItems().stream()
                        .map(OrderLineItemResponse::from)
                        .toList()
        );
    }
}
