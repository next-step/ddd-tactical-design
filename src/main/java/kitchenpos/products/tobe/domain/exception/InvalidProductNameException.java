package kitchenpos.products.tobe.domain.exception;

public class InvalidProductNameException extends IllegalArgumentException {

    public InvalidProductNameException() {
    }

    public InvalidProductNameException(String message) {
        super(message);
    }
}
