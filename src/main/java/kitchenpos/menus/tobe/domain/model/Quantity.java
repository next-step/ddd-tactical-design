package kitchenpos.menus.tobe.domain.model;

import static kitchenpos.menus.tobe.exception.WrongQuantityException.QUANTITY_SHOULD_NOT_BE_NEGATIVE_VALUE;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.menus.tobe.exception.WrongQuantityException;

@Embeddable
public class Quantity {

    @Column(name = "quantity", nullable = false)
    private long value;

    public Quantity(final long value) {
        validate(value);
        this.value = value;
    }

    protected Quantity() {
    }

    private void validate(final long value) {
        if (value < 0) {
            throw new WrongQuantityException(QUANTITY_SHOULD_NOT_BE_NEGATIVE_VALUE);
        }
    }

    public long getValue() {
        return value;
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
        return Objects.hash(value);
    }

}
