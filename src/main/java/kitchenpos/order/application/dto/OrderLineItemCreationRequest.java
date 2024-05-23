package kitchenpos.order.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

import kitchenpos.order.domain.OrderType;

public record OrderLineItemCreationRequest(
    OrderType orderType,
    UUID menuId,
    BigDecimal price,
    long quantity
) {
}
