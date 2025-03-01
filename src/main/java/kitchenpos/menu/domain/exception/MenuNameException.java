package kitchenpos.menu.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class MenuNameException extends IllegalArgumentException {
    public MenuNameException() {
        super(ErrorCode.MENU_NAME_NOT_ALLOWED.toString());
    }
}
