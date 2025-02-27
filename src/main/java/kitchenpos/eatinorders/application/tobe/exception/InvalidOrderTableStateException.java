package kitchenpos.eatinorders.application.tobe.exception;

public class InvalidOrderTableStateException extends IllegalStateException {

    public InvalidOrderTableStateException() {
    }

    public InvalidOrderTableStateException(String message) {
        super(message);
    }
}
