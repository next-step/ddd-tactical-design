package kitchenpos.eatinorders.tobe.exception;

public class IllegalOrderTableException extends RuntimeException {
    public IllegalOrderTableException(final String message) {
        super(message);
    }
}
