package kitchenpos.menus.exception;

public class InvalidMenuProductPriceException extends IllegalArgumentException {

    public InvalidMenuProductPriceException(String message) {
        super(message);
    }
}
