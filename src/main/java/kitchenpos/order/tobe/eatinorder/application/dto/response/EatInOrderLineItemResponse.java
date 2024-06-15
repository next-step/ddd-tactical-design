package kitchenpos.order.tobe.eatinorder.application.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record EatInOrderLineItemResponse(Long seq, UUID menuId, long quantity, BigDecimal price) {
}
