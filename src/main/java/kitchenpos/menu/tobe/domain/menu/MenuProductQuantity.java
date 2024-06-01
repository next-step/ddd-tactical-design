package kitchenpos.menu.tobe.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class MenuProductQuantity {
    @Column(name = "quantity", nullable = false)
    private long quantity;

    public long getQuantity() {
        return quantity;
    }

    protected MenuProductQuantity() {
    }

    public MenuProductQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProductQuantity that = (MenuProductQuantity) o;
        return quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
