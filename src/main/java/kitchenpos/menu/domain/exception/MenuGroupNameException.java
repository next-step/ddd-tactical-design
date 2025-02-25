package kitchenpos.menu.domain.exception;

import kitchenpos.global.exception.ErrorCode;

public class MenuGroupNameException extends IllegalArgumentException {
    public MenuGroupNameException() {
        super(ErrorCode.MENU_GROUP_NAME_NOT_ALLOWED.toString());
    }
}
