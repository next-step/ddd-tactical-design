package kitchenpos.eatinorders.tobe.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

import kitchenpos.eatinorders.tobe.domain.OrderType;

public record OrderLineItemCreationRequest(
    OrderType orderType,
    UUID menuId,
    BigDecimal price,
    long quantity
) {
}
