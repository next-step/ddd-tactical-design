package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NumberOfGuest {
    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    protected NumberOfGuest() {
    }

    private NumberOfGuest(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public static NumberOfGuest of(int numberOfGuests) {
        return new NumberOfGuest(numberOfGuests);
    }

    public static NumberOfGuest defaultNumber() {
        return new NumberOfGuest(0);
    }

    public NumberOfGuest changeNumberOfGuest(int numberOfGuests) {
        validationOfNumber(numberOfGuests);
        return new NumberOfGuest(numberOfGuests);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof NumberOfGuest))
            return false;
        NumberOfGuest that = (NumberOfGuest)o;
        return numberOfGuests == that.numberOfGuests;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfGuests);
    }

    public int getValue() {
        return numberOfGuests;
    }

    private void validationOfNumber(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException("방문한 손님 수는 0 이상이어야 한다.");
        }
    }
}
