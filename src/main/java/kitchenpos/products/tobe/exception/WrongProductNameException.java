package kitchenpos.products.tobe.exception;

public class WrongProductNameException extends RuntimeException {
    public WrongProductNameException() {
    }

    public WrongProductNameException(String message) {
        super(message);
    }
}
