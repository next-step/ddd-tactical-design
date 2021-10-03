package kitchenpos.eatinorders.tobe.domain.model;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class NumberOfGuests {

    private long value;

    protected NumberOfGuests() {

    }

    public NumberOfGuests(long value) {
        this.value = value;
    }

    public Long getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberOfGuests)) return false;
        NumberOfGuests that = (NumberOfGuests) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
