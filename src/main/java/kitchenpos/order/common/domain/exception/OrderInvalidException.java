package kitchenpos.order.common.domain.exception;

public class OrderInvalidException extends IllegalStateException {
    public OrderInvalidException(String message) {
        super(message);
    }
}
