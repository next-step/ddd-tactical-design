package kitchenpos.orders.ordertables.exception;

public class NumberOfGuestException extends RuntimeException {
    private final OrderTableErrorCode message;

    public NumberOfGuestException(OrderTableErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
