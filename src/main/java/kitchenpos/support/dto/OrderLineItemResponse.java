package kitchenpos.support.dto;

import kitchenpos.support.domain.OrderLineItem;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderLineItemResponse(Long seq, UUID menuId, BigDecimal price, long quantity) {
    public static OrderLineItemResponse from(OrderLineItem request) {
        return new OrderLineItemResponse(
                request.getSeq(),
                request.getMenuId(),
                request.getPrice(),
                request.getQuantity()
        );
    }
}

