package kitchenpos.eatinorders.tobe.domain.exception;

public class InvalidNumberOfGuestsException extends RuntimeException {
    public InvalidNumberOfGuestsException(final String message) {
        super(message);
    }
}
