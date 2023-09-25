package kitchenpos.eatinorders.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Quantity {
    private long quantity;

    public Quantity() {}

    private Quantity(long quantity) {
        this.quantity = quantity;
    }

    public static Quantity create(long quantity) {
        return new Quantity(quantity);
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity1 = (Quantity) o;
        return quantity == quantity1.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
