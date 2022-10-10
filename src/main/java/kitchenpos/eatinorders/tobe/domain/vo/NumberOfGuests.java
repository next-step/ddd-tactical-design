package kitchenpos.eatinorders.tobe.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NumberOfGuests {

    @Column(name = "number_of_guests", nullable = false)
    private int value;

    protected NumberOfGuests() {}

    public NumberOfGuests(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("손님수를 확인해주세요.");
        }
        this.value = value;
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
        return value;
    }
}
