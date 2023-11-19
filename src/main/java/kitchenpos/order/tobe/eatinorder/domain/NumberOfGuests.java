package kitchenpos.order.tobe.eatinorder.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NumberOfGuests {

    public static final int MIN_NUMBER_OF_GUESTS = 0;
    public static NumberOfGuests ZERO = new NumberOfGuests(MIN_NUMBER_OF_GUESTS);

    @Column(name = "number_of_guests", nullable = false)
    private int value;

    protected NumberOfGuests() {
    }

    public NumberOfGuests(int value) {
        if (value < MIN_NUMBER_OF_GUESTS) {
            throw new IllegalArgumentException("손님 수는 음수를 허용하지 않습니다.");
        }

        this.value = value;
    }

    public static NumberOfGuests of(int numberOfGuests) {
        return new NumberOfGuests(numberOfGuests);
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumberOfGuests)) {
            return false;
        }
        NumberOfGuests that = (NumberOfGuests) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
