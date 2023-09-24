package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Embeddable;

@Embeddable
public class NumberOfGuests {

    private int number;

    protected NumberOfGuests() {
    }

    public NumberOfGuests(final int number) {
        if (number < 0) {
            throw new IllegalArgumentException();
        }
        this.number = number;
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
        return number;
    }
}
