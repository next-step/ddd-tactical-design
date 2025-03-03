package kitchenpos.order.common.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class OrderMenuInvalidException extends IllegalArgumentException {
    public OrderMenuInvalidException() {
        super(ErrorCode.NOT_FOUND_ORDER_MENU.toString());
    }
}
