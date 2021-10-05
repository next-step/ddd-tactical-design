package kitchenpos.eatinorders.tobe.application.exceptions;

import kitchenpos.eatinorders.tobe.domain.model.ExceptionType;

public abstract class OrderCustomException extends RuntimeException {
    public static void throwException(ExceptionType type) {
        switch (type) {
            case ILLEGAL_ORDER_STATUS:
                throw new OrderStatusException();

            default:
                throw new IllegalArgumentException();
        }
    }
    public OrderCustomException(String message) {
        super(message);
    }
}
