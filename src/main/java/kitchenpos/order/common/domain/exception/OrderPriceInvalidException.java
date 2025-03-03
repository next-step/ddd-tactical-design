package kitchenpos.order.common.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class OrderPriceInvalidException extends IllegalArgumentException {
    public OrderPriceInvalidException() {
        super(ErrorCode.ORDER_PRICE_MISMATCH_MENU_PRICE.toString());
    }
}
