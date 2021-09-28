package kitchenpos.eatinorders.tobe.domain.ordertable;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NumberOfGuests {

    @Column(name = "number_of_guests", nullable = false)
    private int value;

    protected NumberOfGuests() {
    }

    public NumberOfGuests(final int value) {
        validate(value);
        this.value = value;
    }

    private void validate(final int value) {
        if (value < 0) {
            throw new IllegalArgumentException("방문한 손님 수는 0 이상이어야 합니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NumberOfGuests that = (NumberOfGuests) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
