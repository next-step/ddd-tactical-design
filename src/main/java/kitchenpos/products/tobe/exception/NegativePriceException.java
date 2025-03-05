package kitchenpos.products.tobe.exception;

public class NegativePriceException extends RuntimeException {
    public NegativePriceException() {
    }
    public NegativePriceException(String errorMessage) {
        super(errorMessage);
    }
}
