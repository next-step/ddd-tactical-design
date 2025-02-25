package kitchenpos.menus.tobe.domain.exception;

public class InvalidMenuProductsException extends IllegalArgumentException {
    public InvalidMenuProductsException() {
    }

    public InvalidMenuProductsException(String message) {
        super(message);
    }
}
