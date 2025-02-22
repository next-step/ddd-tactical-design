package kitchenpos.menus.tobe.exception;

public class InvalidMenuGroupNameException extends RuntimeException {
    private static final String MESSAGE = "메뉴 그룹 이름은 필수로 입력해야 합니다.";

    public InvalidMenuGroupNameException() {
        super(MESSAGE);
    }
}
