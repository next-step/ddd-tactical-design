package kitchenpos.eatinorders.domain;

import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class NumberOfGuests {
    public static final NumberOfGuests ZERO = new NumberOfGuests(0);

    private int numberOfGuests;

    protected NumberOfGuests() {
    }

    public NumberOfGuests(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException(String.format("손님 수는 0명 이상이어야 합니다. 현재 값: %s", numberOfGuests));
        }
        this.numberOfGuests = numberOfGuests;
    }

    public int intValue() {
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
