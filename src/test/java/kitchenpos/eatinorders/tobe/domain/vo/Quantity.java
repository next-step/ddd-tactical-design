package kitchenpos.eatinorders.tobe.domain.vo;

import java.util.Objects;

public class Quantity {
    private final long value;

    public Quantity(final long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Quantity quantity = (Quantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
