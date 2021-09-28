package kitchenpos.eatinorders.tobe.exception;

public class IllegalOrderException extends RuntimeException {
    public IllegalOrderException(final String message) {
        super(message);
    }
}
