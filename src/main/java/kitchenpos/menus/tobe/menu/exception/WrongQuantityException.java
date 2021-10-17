package kitchenpos.menus.tobe.menu.exception;

public class WrongQuantityException extends IllegalArgumentException {

    public static final String QUANTITY_SHOULD_NOT_BE_NEGATIVE_VALUE = "수량은 음수가 될 수 없습니다";

    public WrongQuantityException(final String s) {
        super(s);
    }

}
