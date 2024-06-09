package kitchenpos.eatinorders.dto;

import kitchenpos.support.dto.OrderLineItemCreateRequest;

import java.util.List;
import java.util.UUID;

public record EatInOrderCreateRequest(List<OrderLineItemCreateRequest> orderLineItems, UUID orderTableId) {
}
