package kitchenpos.menus.domain.tobe.menuproduct;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuQuantity {

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected MenuQuantity() {
    }

    public MenuQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }

        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return BigDecimal.valueOf(this.quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuQuantity that = (MenuQuantity) o;
        return quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
