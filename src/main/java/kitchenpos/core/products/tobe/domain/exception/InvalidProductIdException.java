package kitchenpos.core.products.tobe.domain.exception;

public class InvalidProductIdException extends IllegalArgumentException {
    public InvalidProductIdException(String s) {
        super(s);
    }
}
