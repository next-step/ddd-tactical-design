package kitchenpos.support.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Quantity {
    @Column(name = "quantity", nullable = false)
    private long quantity;


    public Quantity(long quantity) {
        if(quantity < 0) {
            throw new IllegalArgumentException("수량은 0 이상이어야 합니다.");
        }
        this.quantity = quantity;
    }

    protected Quantity() {

    }

    public Quantity add(Quantity num) {
        if(num.isZero()) {
            return this;
        }
        return new Quantity(this.getValue() + num.getValue());
    }

    public Quantity multiply(Quantity num) {
        if(num.isOne()) {
            return this;
        }
        return new Quantity(this.getValue() * num.getValue());
    }

    public long getValue() {
        return quantity;
    }


    public static Quantity Zero() {
        return new Quantity(0);
    }

    public static Quantity One() {
        return new Quantity(1);
    }

    public static Quantity Two() {
        return new Quantity(2);
    }

    public boolean isZero() {
        return this.quantity == 0;
    }

    public boolean isOne() {
        return this.quantity == 1;
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
