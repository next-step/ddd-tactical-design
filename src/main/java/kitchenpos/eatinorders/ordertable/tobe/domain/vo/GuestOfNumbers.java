package kitchenpos.eatinorders.ordertable.tobe.domain.vo;

import kitchenpos.eatinorders.ordertable.tobe.domain.vo.exception.MinimumGuestOfNumbersException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class GuestOfNumbers {

    private static final int MINIMUM_VALUE = 0;

    @Column(name = "number_of_guests", nullable = false)
    private int value;

    protected GuestOfNumbers() {
    }

    private GuestOfNumbers(final int value) {
        this.value = value;
    }

    protected static GuestOfNumbers valueOf(final int value) {
        if (value < MINIMUM_VALUE) {
            throw new MinimumGuestOfNumbersException(value);
        }
        return new GuestOfNumbers(value);
    }

    public int value() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GuestOfNumbers that = (GuestOfNumbers) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
