package kitchenpos.eatinorders.tobe.exception;

public class IllegalStatusChangeException extends RuntimeException {
    public IllegalStatusChangeException(final String message) {
        super(message);
    }
}
