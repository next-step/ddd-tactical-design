package kitchenpos.orders.ordertables.domain;

import kitchenpos.common.domain.ValueObject;
import kitchenpos.orders.ordertables.exception.NumberOfGuestException;
import kitchenpos.orders.ordertables.exception.OrderTableErrorCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NumberOfGuest extends ValueObject {

    public static final NumberOfGuest ZERO = new NumberOfGuest(0);

    @Column(name = "number_of_guests", nullable = false)
    private int value;

    protected NumberOfGuest() {

    }

    public NumberOfGuest(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < 0) {
            throw new NumberOfGuestException(OrderTableErrorCode.NUMBER_OF_GUEST_IS_GREATER_THAN_ZERO);
        }
    }

    public int getValue() {
        return value;
    }
}
