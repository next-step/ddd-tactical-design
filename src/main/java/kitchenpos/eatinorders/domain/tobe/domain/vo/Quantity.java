package kitchenpos.eatinorders.domain.tobe.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Quantity {
    private final int ZERO = 0;
    private final int ONE = 1;

    @Column(name = "quantity", nullable = false)
    private long quantity;


    public Quantity(long quantity) {
        this.quantity = quantity;
    }

    protected Quantity() {

    }

    public static Quantity of(long quantity) {
        return new Quantity(quantity);
    }

    public Quantity add(Quantity num) {
        if (num.isZero()) {
            return this;
        }
        return new Quantity(this.getValue() + num.getValue());
    }

    public Quantity multiply(Quantity num) {
        if (num.isOne()) {
            return this;
        }
        return new Quantity(this.getValue() * num.getValue());
    }

    public long getValue() {
        return quantity;
    }

    public boolean isZero() {
        return this.quantity == ZERO;
    }

    public boolean isOne() {
        return this.quantity == ONE;
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
        return (int) (quantity ^ (quantity >>> 32));
    }
}
