package kitchenpos.menu.domain.exception;

import java.util.UUID;

public class MenuGroupNotFoundException extends IllegalArgumentException {
    private static final String MESSAGE = "메뉴 그룹을 찾을 수 없습니다. id=";

    public MenuGroupNotFoundException(UUID menuGroupId) {
        super(MESSAGE + menuGroupId);
    }
}
