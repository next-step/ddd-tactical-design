package kitchenpos.eatinorders.tobe.domain.exception;

public class InvalidOrderStatusException extends IllegalArgumentException {
    public InvalidOrderStatusException() {
    }

    public InvalidOrderStatusException(String message) {
        super(message);
    }
}
