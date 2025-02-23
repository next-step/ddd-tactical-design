package kitchenpos.menus.tobe.domain.exception;

public class MenuNameContainsProfanityException extends RuntimeException {
    private static final String MESSAGE = "메뉴의 이름에는 비속어가 포함될 수 없습니다.";

    public MenuNameContainsProfanityException() {
        super(MESSAGE);
    }
}
