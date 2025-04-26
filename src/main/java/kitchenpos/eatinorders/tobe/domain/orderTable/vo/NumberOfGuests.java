package kitchenpos.eatinorders.tobe.domain.orderTable.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidNumberOfGuestsException;

import java.util.Objects;

@Embeddable
public class NumberOfGuests {
    private static final int MINIMUM_NUMBER = 0;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    protected NumberOfGuests() {
    }

    public NumberOfGuests(final int numberOfGuests) {
        if (numberOfGuests < MINIMUM_NUMBER) {
            throw new InvalidNumberOfGuestsException("방문한 손님 수가 0명 이상이어야 합니다.");
        }
        this.numberOfGuests = numberOfGuests;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberOfGuests that)) return false;
        return numberOfGuests == that.numberOfGuests;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfGuests);
    }
}
