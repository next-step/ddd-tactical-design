package kitchenpos.products.tobe.exception.message;

public class ProductPriceException extends RuntimeException{
    private final ProductErrorCode message;

    public ProductPriceException(ProductErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
