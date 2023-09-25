package kitchenpos.eatinorders.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.util.Objects;

import static kitchenpos.eatinorders.exception.OrderTableExceptionMessage.NUMBER_GUESTS_NEGATIVE;

@Embeddable
public class NumberOfGuests {
    public static final NumberOfGuests ZERO = NumberOfGuests.create(0);
    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    protected NumberOfGuests() {}

    private NumberOfGuests(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException(NUMBER_GUESTS_NEGATIVE);
        }
        this.numberOfGuests = numberOfGuests;
    }


    public static NumberOfGuests create(Integer number) {
        return new NumberOfGuests(number);
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberOfGuests that = (NumberOfGuests) o;
        return numberOfGuests == that.numberOfGuests;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfGuests);
    }
}
