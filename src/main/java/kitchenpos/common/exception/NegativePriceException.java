package kitchenpos.common.exception;

public class NegativePriceException extends IllegalArgumentException {
    public NegativePriceException() {
    }

    public NegativePriceException(String message) {
        super(message);
    }
}
