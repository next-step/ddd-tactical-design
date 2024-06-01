package kitchenpos.eatinorders.exception;

public class OrderIllegalArgumentException extends IllegalArgumentException {

    public OrderIllegalArgumentException(OrderExceptionCode code) {
        super(code.message());
    }
}
