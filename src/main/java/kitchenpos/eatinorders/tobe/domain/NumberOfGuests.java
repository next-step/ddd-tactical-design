package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class NumberOfGuests {
    private static final String NUMBER_OF_GUESTS_MUST_NOT_BE_NEGATIVE = "방문한 손님 수는 0 이상이어야 합니다. 입력값 : %s";

    @Column(name = "number_of_guests", nullable = false)
    private final int numberOfGuests;

    protected NumberOfGuests() {
        this.numberOfGuests = 0;
    }

    protected NumberOfGuests(int numberOfGuests) {
        validate(numberOfGuests);
        this.numberOfGuests = numberOfGuests;
    }

    private void validate(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalStateException(String.format(NUMBER_OF_GUESTS_MUST_NOT_BE_NEGATIVE, numberOfGuests));
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
