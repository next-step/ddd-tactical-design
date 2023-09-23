package kitchenpos.orders.eatinorders.exception;

public class EatInOrderException extends RuntimeException {
    private final EatInOrderErrorCode message;

    public EatInOrderException(EatInOrderErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
