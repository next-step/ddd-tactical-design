package kitchenpos.menus.tobe.domain.exception;

public class MenuProductCountMismatchException extends RuntimeException {

    public MenuProductCountMismatchException(final String message) {
        super(message);
    }
}
