package kitchenpos.products.exception;

public class InvalidProductPriceException extends IllegalArgumentException {

    public InvalidProductPriceException(String message) {
        super(message);
    }
}
