package kitchenpos.products.exception;

public class ProductNameException extends RuntimeException {
    private final ProductErrorCode message;

    public ProductNameException(ProductErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
