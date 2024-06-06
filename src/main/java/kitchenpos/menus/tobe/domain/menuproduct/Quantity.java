package kitchenpos.menus.tobe.domain.menuproduct;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Quantity {

    @Column(name = "quantity", nullable = false)
    private int quantity;

    private Quantity() {
    }

    public Quantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("상품의 수량은 0 이상이어야 한다.");
        }

        this.quantity = quantity;
    }

    int getQuantity() {
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
        return Objects.hashCode(quantity);
    }
}
