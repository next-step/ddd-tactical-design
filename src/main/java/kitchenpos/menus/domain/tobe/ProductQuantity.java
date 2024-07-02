package kitchenpos.menus.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductQuantity {

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected ProductQuantity() {
    }

    public ProductQuantity(long quantity) {
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
        ProductQuantity that = (ProductQuantity) o;
        return quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
