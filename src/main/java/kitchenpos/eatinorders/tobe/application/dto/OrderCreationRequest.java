package kitchenpos.eatinorders.tobe.application.dto;

import java.util.List;
import java.util.UUID;

import kitchenpos.eatinorders.tobe.domain.OrderType;

public record OrderCreationRequest(
    OrderType type,
    List<OrderLineItemCreationRequest> orderLineItemsRequest,
    String deliveryAddress,
    UUID orderTableId
) {
}