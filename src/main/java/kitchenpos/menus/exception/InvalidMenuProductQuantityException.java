package kitchenpos.menus.exception;

public class InvalidMenuProductQuantityException extends IllegalArgumentException {

    public InvalidMenuProductQuantityException(String message) {
        super(message);
    }
}
