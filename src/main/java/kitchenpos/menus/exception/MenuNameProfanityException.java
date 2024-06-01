package kitchenpos.menus.exception;

public class MenuNameProfanityException extends IllegalArgumentException {
    public MenuNameProfanityException(String value) {
        super("메뉴 이름에는 비속어가 포함될 수 없다. value : " + value);
    }
}
