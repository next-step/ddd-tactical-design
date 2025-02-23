package kitchenpos.menus.tobe.domain.exception;

public class InvalidMenuIdException extends RuntimeException {
    private static final String MESSAGE = "메뉴 ID가 유효하지 않습니다.";

    public InvalidMenuIdException() {
        super(MESSAGE);
    }
}
