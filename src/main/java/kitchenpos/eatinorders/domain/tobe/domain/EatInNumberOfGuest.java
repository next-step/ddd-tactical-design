package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EatInNumberOfGuest {
    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    protected EatInNumberOfGuest() {
    }

    private EatInNumberOfGuest(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public static EatInNumberOfGuest of(int numberOfGuests) {
        return new EatInNumberOfGuest(numberOfGuests);
    }

    public static EatInNumberOfGuest defaultNumber() {
        return new EatInNumberOfGuest(0);
    }

    public EatInNumberOfGuest changeNumberOfGuest(int numberOfGuests) {
        validationOfNumber(numberOfGuests);
        return new EatInNumberOfGuest(numberOfGuests);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof EatInNumberOfGuest))
            return false;
        EatInNumberOfGuest that = (EatInNumberOfGuest)o;
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
