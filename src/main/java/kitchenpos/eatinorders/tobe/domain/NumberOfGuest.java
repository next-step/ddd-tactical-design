package kitchenpos.eatinorders.tobe.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NumberOfGuest {

    public static NumberOfGuest INIT = new NumberOfGuest(0);

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    public NumberOfGuest(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException("손님 수는 0보다 적을수 없습니다");
        }
        this.numberOfGuests = numberOfGuests;
    }

    protected NumberOfGuest() {
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumberOfGuest)) {
            return false;
        }
        NumberOfGuest that = (NumberOfGuest) o;
        return numberOfGuests == that.numberOfGuests;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfGuests);
    }
}
