package kitchenpos.menus.domain.tobe.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Quantity {
    @Column(name = "quantity", nullable = false)
    private long quantity;

    public Quantity() {
    }

    private Quantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("수량은 0보다 작을 수 없습니다.");
        }
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Quantity quantity1 = (Quantity)o;
        return quantity == quantity1.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }

    public static Quantity of(long quantity) {
        return new Quantity(quantity);
    }

    public long getValue() {
        return quantity;
    }

    public Quantity changeQuantity(long quantity) {
        return new Quantity(quantity);
    }

}
