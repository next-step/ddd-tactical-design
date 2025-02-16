package kitchenpos.product.domain.exception;

public class ProductPriceValidationException extends IllegalArgumentException {
    public ProductPriceValidationException(String message) {
        super(message);
    }
}
