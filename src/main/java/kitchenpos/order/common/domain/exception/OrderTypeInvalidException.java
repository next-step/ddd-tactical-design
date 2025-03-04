package kitchenpos.order.common.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class OrderTypeInvalidException extends IllegalStateException {
    public OrderTypeInvalidException() {
        super(ErrorCode.ORDER_TYPE_IS_NOT_ALLOWED.toString());
    }
}
