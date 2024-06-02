package kitchenpos.eatinorders.todo.domain.ordertables;

import jakarta.persistence.Embeddable;
import kitchenpos.eatinorders.exception.KitchenPosIllegalArgumentException;

import java.util.Objects;

import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.INVALID_ORDER_TABLE_GUESTS_NUMBER;

@Embeddable
public class NumberOfGuests {
    static final NumberOfGuests ZERO_NUMBER_OF_GUESTS = new NumberOfGuests();
    private int number = 0;

    protected NumberOfGuests() {
    }

    protected NumberOfGuests(int number) {
        validate(number);
        this.number = number;
    }

    public static NumberOfGuests from(int number) {
        return new NumberOfGuests(number);
    }

    public int number() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberOfGuests that = (NumberOfGuests) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    private void validate(int number) {
        if (number < 0) {
            throw new KitchenPosIllegalArgumentException(INVALID_ORDER_TABLE_GUESTS_NUMBER, number);
        }
    }
}
