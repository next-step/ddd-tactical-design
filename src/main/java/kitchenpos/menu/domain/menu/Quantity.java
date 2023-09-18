package kitchenpos.menu.domain.menu;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class Quantity {

    private int value;

    protected Quantity() {
    }

    private Quantity(final int value) {
        this.value = value;
    }

    public static Quantity create(final int value) {
        checkArgument(value > 0, "quantity must not be less than zero: %s", value);

        return new Quantity(value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Quantity quantity = (Quantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    public int getValue() {
        return value;
    }
}
