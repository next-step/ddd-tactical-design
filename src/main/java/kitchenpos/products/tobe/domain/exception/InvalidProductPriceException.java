package kitchenpos.products.tobe.domain.exception;

public class InvalidProductPriceException extends IllegalArgumentException {
    public InvalidProductPriceException(String s) {
        super(s);
    }
}
