package kitchenpos.eatinorders.tobe.domain.ordertable.vo;

import java.util.Objects;

public class NumberOfGuests {
    private static final int MINIMUM_VALUE = 0;

    private final int value;

    public NumberOfGuests(final int value) {
        if (value < MINIMUM_VALUE) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final NumberOfGuests that = (NumberOfGuests) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
