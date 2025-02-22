package kitchenpos.common.exception;

public class NegativeNumberException extends IllegalArgumentException {
    public NegativeNumberException() {
    }

    public NegativeNumberException(String message) {
        super(message);
    }
}
