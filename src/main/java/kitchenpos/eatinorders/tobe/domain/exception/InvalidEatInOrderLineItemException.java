package kitchenpos.eatinorders.tobe.domain.exception;

public class InvalidEatInOrderLineItemException extends RuntimeException {
    public InvalidEatInOrderLineItemException(final String message) {
        super(message);
    }
}
