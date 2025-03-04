package kitchenpos.order.delivery.exception;

import kitchenpos.global.exception.ErrorCode;

public class DeliveryInfoException extends IllegalArgumentException {
    public DeliveryInfoException() {
        super(ErrorCode.DELIVERY_ADDRESS_NOT_ALLOWED.toString());
    }
}
