package kitchenpos.common.exception;

public class PriceException extends RuntimeException {
    private final PriceErrorCode message;

    public PriceException(PriceErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
