package kitchenpos.menus.tobe.domain.exception;

public class InvalidMenuGroupIdException extends RuntimeException {
    private static final String MESSAGE = "메뉴 그룹 ID가 유효하지 않습니다.";

    public InvalidMenuGroupIdException() {
        super(MESSAGE);
    }
}
