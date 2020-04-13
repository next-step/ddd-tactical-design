package kitchenpos.eatinorders.tobe.order.domain.exception;

public class OrderAlreadyCompletedException extends RuntimeException {
    public OrderAlreadyCompletedException(final String message) {
        super(message);
    }
}
