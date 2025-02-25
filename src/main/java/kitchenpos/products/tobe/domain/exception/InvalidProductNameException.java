package kitchenpos.products.tobe.domain.exception;

public class InvalidProductNameException extends IllegalArgumentException {
    public InvalidProductNameException(String s) {
        super(s);
    }
}
