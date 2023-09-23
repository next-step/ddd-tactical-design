package kitchenpos.eatinorders.domain;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
public class Quantity {
    private int quantity;

    protected Quantity() {
    }

    public Quantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(String.format("수량은 0개 이상이어야 합니다. 현재 값: %s", quantity));
        }
        this.quantity = quantity;
    }

    public int intValue() {
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
