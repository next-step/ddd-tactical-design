package kitchenpos.eatinorders.domain.ordertables;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class NumberOfGuests {
    public static final NumberOfGuests ZERO = new NumberOfGuests(0);
    private static final int MINIMUM_NUMBER_OF_GUESTS = 0;

    @Column(name = "number_of_guests", nullable = false)
    private int value;

    protected NumberOfGuests() {
    }

    public NumberOfGuests(final int value) {
        if (isNegativeNumber(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    private static boolean isNegativeNumber(int value) {
        return value < MINIMUM_NUMBER_OF_GUESTS;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberOfGuests that = (NumberOfGuests) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
