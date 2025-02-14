package kitchenpos.product.application.exception;

public class ProductNameValidationException extends IllegalArgumentException {
    public ProductNameValidationException(String message) {
        super(message);
    }
}
