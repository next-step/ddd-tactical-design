package kitchenpos.eatinorders.tobe.domain.exception;

public class InvalidOrderTableNameException extends IllegalArgumentException {

    public InvalidOrderTableNameException() {
    }

    public InvalidOrderTableNameException(String message) {
        super(message);
    }
}
