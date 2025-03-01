package kitchenpos.menu.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class MenuPriceException extends IllegalArgumentException {
    public MenuPriceException() {
        super(ErrorCode.MENU_PRICE_NOT_ALLOWED.toString());
    }
}
