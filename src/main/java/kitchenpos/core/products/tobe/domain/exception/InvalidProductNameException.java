package kitchenpos.core.products.tobe.domain.exception;

public class InvalidProductNameException extends IllegalArgumentException {
    public InvalidProductNameException(String s) {
        super(s);
    }
}
