package kitchenpos.ordertables.exception;

public class OrderTableNameException extends RuntimeException {
    private final OrderTableErrorCode message;

    public OrderTableNameException(OrderTableErrorCode message) {
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
