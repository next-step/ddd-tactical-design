package kitchenpos.menu.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class MenuProductQtyException extends IllegalArgumentException {
    public MenuProductQtyException() {
        super(ErrorCode.MENU_PRODUCT_QTY_NOT_ALLOWED.toString());
    }
}
