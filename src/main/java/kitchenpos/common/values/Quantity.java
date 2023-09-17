package kitchenpos.common.values;

import kitchenpos.common.domain.Purgomalum;

import javax.persistence.Column;
import java.util.Objects;

public class Quantity {

    @Column(name = "quantity", nullable = false)
    private Long value;


    protected Quantity() {
    }

    public Quantity(final Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public boolean equalValue(Long value) {
        return this.value.equals(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity = (Quantity) o;
        return Objects.equals(value, quantity.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
