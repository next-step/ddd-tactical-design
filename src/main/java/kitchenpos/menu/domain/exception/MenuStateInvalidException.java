package kitchenpos.menu.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class MenuStateInvalidException extends IllegalStateException {
    public MenuStateInvalidException() {
        super(ErrorCode.MENU_PRICE_OVER_TOTAL_PRODUCTS_NOT_ALLOWED.toString());
    }
}
