package kitchenpos.eatinorders.tobe.domain.vo;

import java.util.Objects;

public class Occupied {
    private final boolean value;

    public Occupied(final boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Occupied occupied = (Occupied) o;
        return value == occupied.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
