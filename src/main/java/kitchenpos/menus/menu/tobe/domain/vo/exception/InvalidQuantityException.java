package kitchenpos.menus.menu.tobe.domain.vo.exception;

public class InvalidQuantityException extends IllegalArgumentException {

    private static final String MESSAGE = "수량은 0개 이상이여야 합니다. value=%d";

    public InvalidQuantityException(final Long value) {
        super(String.format(MESSAGE, value));
    }
}
