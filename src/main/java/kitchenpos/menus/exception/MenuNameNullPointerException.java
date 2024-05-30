package kitchenpos.menus.exception;

public class MenuNameNullPointerException extends IllegalArgumentException {
    public MenuNameNullPointerException() {
        super("메뉴 이름은 비워둘 수 없다.");
    }
}
