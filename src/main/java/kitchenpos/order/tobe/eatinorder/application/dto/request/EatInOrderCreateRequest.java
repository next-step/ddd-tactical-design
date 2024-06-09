package kitchenpos.order.tobe.eatinorder.application.dto.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record EatInOrderCreateRequest(String orderType,
                                      List<OrderLineItemsRequestDto> orderLineItems,
                                      UUID orderTableId) {
    public record OrderLineItemsRequestDto(UUID menuId, long quantity, BigDecimal price) {
    }

}
