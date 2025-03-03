package kitchenpos.order.common.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class OrderLineItemQtyException extends IllegalArgumentException {
    public OrderLineItemQtyException() {
        super(ErrorCode.ORDER_LINE_ITEM_QTY_NOT_ALLOWED.toString());
    }
}
