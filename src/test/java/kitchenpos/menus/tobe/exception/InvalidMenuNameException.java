package kitchenpos.menus.tobe.exception;

public class InvalidMenuNameException extends RuntimeException {
    private static final String MESSAGE = "메뉴 이름은 필수로 입력해야 합니다.";

    public InvalidMenuNameException() {
        super(MESSAGE);
    }
}
