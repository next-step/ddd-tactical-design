package kitchenpos.eatinorders.tobe.domain.exception;

public class InvalidEatInOrderTableException extends RuntimeException {
    public InvalidEatInOrderTableException(final String message) {
        super(message);
    }
}
