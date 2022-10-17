package kitchenpos.eatinorders.feedback.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class NumberOfGuest {
    private static final int MIN_NUMBER_OF_GUEST = 0;

    @Column(name = "number_of_guest")
    private int numberOfGuest;

    public NumberOfGuest() {
        this(0);
    }

    public NumberOfGuest(int numberOfGuest) {
        if (numberOfGuest < MIN_NUMBER_OF_GUEST) {
            throw new IllegalArgumentException("손님 수는 " + MIN_NUMBER_OF_GUEST + "이상이어야 합니다.");
        }
        this.numberOfGuest = numberOfGuest;
    }

    public int getNumberOfGuest() {
        return numberOfGuest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberOfGuest that = (NumberOfGuest) o;
        return numberOfGuest == that.numberOfGuest;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfGuest);
    }
}
