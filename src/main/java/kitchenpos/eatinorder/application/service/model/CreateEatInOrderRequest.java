package kitchenpos.eatinorder.application.service.model;

import java.util.List;
import java.util.UUID;

public record CreateEatInOrderRequest(UUID orderTableId, List<OrderLineItemRequest> orderLineItems) {
}
