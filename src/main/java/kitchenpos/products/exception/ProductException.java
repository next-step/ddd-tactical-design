package kitchenpos.products.exception;

public class ProductException extends RuntimeException {
    private final ProductErrorCode message;

    public ProductException(ProductErrorCode message) {
        this.message = message;
    }

    public ProductErrorCode getErrorCode() {
        return message;
    }

}
