package kitchenpos.eatinorders.tobe.domain.exception;

public class InvalidEatInOrderException extends RuntimeException {

    public InvalidEatInOrderException() {
        super();
    }

    public InvalidEatInOrderException(final String message) {
        super(message);
    }
}
