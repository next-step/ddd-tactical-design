package kitchenpos.ordertables.exception;

public class OrderTableException extends RuntimeException {
    private final OrderTableErrorCode message;

    public OrderTableException(OrderTableErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
