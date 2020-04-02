package kitchenpos.eatinorders.tobe.table.domain.exception;

public class OrderNotCompletedException extends RuntimeException {

    public OrderNotCompletedException(final String message) {
        super(message);
    }
}
