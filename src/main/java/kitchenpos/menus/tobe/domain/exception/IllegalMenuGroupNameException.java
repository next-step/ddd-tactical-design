package kitchenpos.menus.tobe.domain.exception;

public class IllegalMenuGroupNameException extends IllegalArgumentException {

    private static final String DEFAULT_MESSAGE = "메뉴그룹의 이름이 올바르지 않습니다..";

    public IllegalMenuGroupNameException() {
        super(DEFAULT_MESSAGE);
    }

    public IllegalMenuGroupNameException(String message) {
        super(message);
    }
}
