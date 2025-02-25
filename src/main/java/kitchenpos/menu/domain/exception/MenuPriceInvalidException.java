package kitchenpos.menu.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class MenuPriceInvalidException extends IllegalStateException {
    public MenuPriceInvalidException() {
        super(ErrorCode.MENU_PRICE_OVER_TOTAL_PRODUCTS_NOT_ALLOWED.toString());
    }
}
