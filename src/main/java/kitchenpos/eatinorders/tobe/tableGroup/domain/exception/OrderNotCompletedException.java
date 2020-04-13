package kitchenpos.eatinorders.tobe.tableGroup.domain.exception;

public class OrderNotCompletedException extends RuntimeException {

    public OrderNotCompletedException(final String message) {
        super(message);
    }
}
