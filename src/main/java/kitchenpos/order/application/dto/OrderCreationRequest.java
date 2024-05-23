package kitchenpos.order.application.dto;

import java.util.List;
import java.util.UUID;

import kitchenpos.order.domain.OrderType;

public record OrderCreationRequest(
    OrderType type,
    List<OrderLineItemCreationRequest> orderLineItemsRequest,
    String deliveryAddress,
    UUID orderTableId
) {
}