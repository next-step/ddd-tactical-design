package kitchenpos.eatinorders.tobe.domain.exception;

public class InvalidOrderTableException extends IllegalStateException {
    public InvalidOrderTableException() {
    }

    public InvalidOrderTableException(String message) {
        super(message);
    }
}
