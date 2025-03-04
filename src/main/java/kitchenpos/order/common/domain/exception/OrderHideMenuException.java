package kitchenpos.order.common.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class OrderHideMenuException extends IllegalStateException {
    public OrderHideMenuException() {
        super(ErrorCode.ORDER_HIDE_MENU_NOT_ALLOWED.toString());
    }
}
