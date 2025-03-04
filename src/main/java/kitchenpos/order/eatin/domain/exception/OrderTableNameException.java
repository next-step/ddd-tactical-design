package kitchenpos.order.eatin.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class OrderTableNameException extends IllegalArgumentException {
    public OrderTableNameException() {
        super(ErrorCode.ORDER_TABLE_NAME_NOT_ALLOWED.toString());
    }
}
