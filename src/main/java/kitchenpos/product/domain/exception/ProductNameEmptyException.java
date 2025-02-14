package kitchenpos.product.domain.exception;

public class ProductNameEmptyException extends IllegalArgumentException {
    public ProductNameEmptyException(String message) {
        super(message);
    }
}
