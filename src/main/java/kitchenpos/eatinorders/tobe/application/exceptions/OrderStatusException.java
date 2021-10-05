package kitchenpos.eatinorders.tobe.application.exceptions;

public class OrderStatusException extends OrderCustomException {

    private static final String ILLEGAL_ORDER_STATUS_MESSAGE = "잘못된 Order Status 입니다.";

    public OrderStatusException() {
        super(ILLEGAL_ORDER_STATUS_MESSAGE);
    }
}
