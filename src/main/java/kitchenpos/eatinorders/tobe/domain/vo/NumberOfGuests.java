package kitchenpos.eatinorders.tobe.domain.vo;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.global.vo.ValueObject;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
public class NumberOfGuests implements ValueObject {

    @Column(name = "number_of_guests", nullable = false)
    @ColumnDefault("0")
    private long value;

    protected NumberOfGuests() {
    }

    public NumberOfGuests(long value) {
        validate(value);
        this.value = value;
    }

    private void validate(long value) {
        if (value < 0) {
            throw new IllegalArgumentException();
        }
    }

    public static NumberOfGuests zero() {
        return new NumberOfGuests(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NumberOfGuests that = (NumberOfGuests) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
