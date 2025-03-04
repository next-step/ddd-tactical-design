package kitchenpos.order.common.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class OrderLineItemQtyException extends IllegalArgumentException {
    public OrderLineItemQtyException(String message) {
        super(message);
    }
}
