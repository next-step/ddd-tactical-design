package kitchenpos.orders.common.application.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record OrderLineItemsRequest(@NotEmpty List<OrderLineItemRequest> orderLineItems) {

}
