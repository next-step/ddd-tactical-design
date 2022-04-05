package kitchenpos.eatinorders.tobe.eatinorder.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Quantity {

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected Quantity() {
    }

    public Quantity(final long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal value() {
        return BigDecimal.valueOf(quantity);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Quantity quantity1 = (Quantity) o;
        return quantity == quantity1.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
