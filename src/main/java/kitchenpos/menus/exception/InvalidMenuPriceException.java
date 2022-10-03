package kitchenpos.menus.exception;

public class InvalidMenuPriceException extends IllegalArgumentException {

    public InvalidMenuPriceException(String message) {
        super(message);
    }
}
