package kitchenpos.product.domain.exception;

public class ProductNameValidationException extends IllegalArgumentException {
    public ProductNameValidationException(String message) {
        super(message);
    }
}
