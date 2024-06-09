package kitchenpos.support.dto;

import kitchenpos.support.domain.OrderLineItem;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderLineItemCreateRequest(UUID menuId, BigDecimal price, long quantity) {
    public OrderLineItem toEntity() {
        return new OrderLineItem(menuId, price, quantity);
    }
}
