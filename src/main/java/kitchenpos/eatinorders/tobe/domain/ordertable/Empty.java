package kitchenpos.eatinorders.tobe.domain.ordertable;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Empty {

    @Column(name = "empty", nullable = false)
    private boolean value;

    protected Empty() {
    }

    public Empty(final boolean value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return this.value;
    }

    public boolean isTaken() {
        return !this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Empty empty = (Empty) o;
        return value == empty.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
