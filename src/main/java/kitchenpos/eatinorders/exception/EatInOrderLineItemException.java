package kitchenpos.eatinorders.exception;

public class EatInOrderLineItemException extends RuntimeException {
    private final EatInOrderErrorCode message;

    public EatInOrderLineItemException(EatInOrderErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
