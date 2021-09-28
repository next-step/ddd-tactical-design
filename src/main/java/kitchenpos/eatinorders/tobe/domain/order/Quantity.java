package kitchenpos.eatinorders.tobe.domain.order;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Quantity {

    @Column(name = "quantity", nullable = false)
    private long value;

    protected Quantity() {
    }

    public Quantity(final long value) {
        this.value = value;
    }

    public BigDecimal toBigDecimal() {
        return BigDecimal.valueOf(value);
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
