package kitchenpos.eatinorders.tobe.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class NumberOfGuests {
    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    protected NumberOfGuests() {}

    public static NumberOfGuests zero() {
        return new NumberOfGuests(0);
    }

    public NumberOfGuests(int numberOfGuests) {
        checkValidatedNumber(numberOfGuests);
        this.numberOfGuests = numberOfGuests;
    }

    private void checkValidatedNumber(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
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
