package kitchenpos.menus.tobe.domain.exception;

public class InvalidMenuProductQuantityException extends RuntimeException {

    public InvalidMenuProductQuantityException(final String message) {
        super(message);
    }
}
