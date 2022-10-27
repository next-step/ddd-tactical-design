package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class NumberOfGuests {
    private int numberOfGuests;

    protected NumberOfGuests() {
    }

    public NumberOfGuests(int numberOfGuests) {
        validateNumberOfGuest(numberOfGuests);
        this.numberOfGuests = numberOfGuests;
    }

    public void changeNumberOfGuest(int numberOfGuests) {
        validateNumberOfGuest(numberOfGuests);
        this.numberOfGuests = numberOfGuests;
    }

    public int numberOfGuests() {
        return numberOfGuests;
    }

    private void validateNumberOfGuest(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
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
