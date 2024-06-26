package kitchenpos.order.tobe.eatinorder.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EatInOrderResponse(UUID id, UUID orderTableId, List<EatInOrderLineItemResponse> orderLineItems,
                                 String orderStatus, LocalDateTime orderedTime, OrderTableResponse orderTable) {
}
