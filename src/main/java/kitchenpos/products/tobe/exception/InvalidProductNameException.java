package kitchenpos.products.tobe.exception;

public class InvalidProductNameException extends RuntimeException {
    public InvalidProductNameException() {
    }
    public InvalidProductNameException(String message) {
        super(message);
    }
}
