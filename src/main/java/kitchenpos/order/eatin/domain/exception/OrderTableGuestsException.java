package kitchenpos.order.eatin.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class OrderTableGuestsException extends IllegalArgumentException {
    public OrderTableGuestsException() {
        super(ErrorCode.ORDER_TABLE_NUMBER_OF_GUESTS_NOT_ALLOWED.toString());
    }
}
