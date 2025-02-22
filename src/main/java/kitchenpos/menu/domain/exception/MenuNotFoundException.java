package kitchenpos.menu.domain.exception;

import java.util.UUID;

public class MenuNotFoundException extends IllegalArgumentException {
    private static final String MESSAGE = "메뉴를 찾을 수 없습니다. id=";
    public MenuNotFoundException(UUID id) {
        super(MESSAGE + id);
    }
}
