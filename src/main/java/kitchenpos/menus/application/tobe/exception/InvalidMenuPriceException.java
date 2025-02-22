package kitchenpos.menus.application.tobe.exception;

public class InvalidMenuPriceException extends IllegalArgumentException {
    public InvalidMenuPriceException() {
    }

    public InvalidMenuPriceException(String message) {
        super(message);
    }
}
