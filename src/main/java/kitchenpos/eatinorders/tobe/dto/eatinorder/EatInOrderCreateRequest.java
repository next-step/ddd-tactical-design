package kitchenpos.eatinorders.tobe.dto.eatinorder;

import java.util.List;
import java.util.UUID;
import kitchenpos.shared.domain.OrderType;

public record EatInOrderCreateRequest(OrderType type,
                                      UUID orderTableId,
                                      List<EatInOrderLineItemRequest> orderLineItems) {

}
