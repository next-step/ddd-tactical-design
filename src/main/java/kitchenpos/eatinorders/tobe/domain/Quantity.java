package kitchenpos.eatinorders.tobe.domain;

import java.util.Objects;

public class Quantity {

    private long quantity;

    public static Quantity from(long quantity) {
        return new Quantity(quantity);
    }

    protected Quantity() {
        this(0);
    }

    private Quantity(long quantity) {
        this.quantity = quantity;
    }

    public long quantity() {
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
