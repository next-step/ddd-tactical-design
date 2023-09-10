package kitchenpos.products.exception;

public class ProductDisplayedNameException extends RuntimeException {
    private final ProductErrorCode message;

    public ProductDisplayedNameException(ProductErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
