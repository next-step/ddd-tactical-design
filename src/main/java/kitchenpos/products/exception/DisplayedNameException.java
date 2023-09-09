package kitchenpos.products.exception;

public class DisplayedNameException extends RuntimeException {
    private final ProductErrorCode message;

    public DisplayedNameException(ProductErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
