package kitchenpos.eatinorders.dto;

import java.util.List;
import java.util.UUID;

public record EatInOrderCreateRequest(
        List<OrderLineItemCreateRequest> orderLineItems, UUID orderTableId
) {
}
