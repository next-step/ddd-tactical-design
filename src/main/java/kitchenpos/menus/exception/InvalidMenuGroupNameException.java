package kitchenpos.menus.exception;

public class InvalidMenuGroupNameException extends IllegalArgumentException {

    private static final String EXCEPTION_MESSAGE = "메뉴그룹 이름은 비워둘 수 없다.";

    public InvalidMenuGroupNameException() {
        super(EXCEPTION_MESSAGE);
    }
}
