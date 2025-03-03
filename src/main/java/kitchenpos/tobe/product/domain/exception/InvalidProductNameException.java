package kitchenpos.tobe.product.domain.exception;

public class InvalidProductNameException extends IllegalArgumentException {
    public InvalidProductNameException(String message) {
        super(message);
    }
}
