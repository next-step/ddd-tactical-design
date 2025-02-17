package kitchenpos.products.tobe.domain.exception;

public class NegativePriceException extends IllegalArgumentException {
    public NegativePriceException() {
    }

    public NegativePriceException(String message) {
        super(message);
    }
}
