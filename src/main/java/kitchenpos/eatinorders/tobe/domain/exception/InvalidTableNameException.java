package kitchenpos.eatinorders.tobe.domain.exception;

public class InvalidTableNameException extends RuntimeException {
    public InvalidTableNameException(final String message) {
        super(message);
    }
}
