package kitchenpos.eatinorders.tobe.domain.exception;

public class UncompletedOrdersExistException extends IllegalStateException {
    public UncompletedOrdersExistException(final String message) {
        super(message);
    }
}
