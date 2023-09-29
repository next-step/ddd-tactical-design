package kitchenpos.eatinorders.tobe.domain.order;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class EatInOrderLineItemQuantity {
    @Column(name = "quantity")
    private long quantity;

    protected EatInOrderLineItemQuantity() {
    }

    public EatInOrderLineItemQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrderLineItemQuantity that = (EatInOrderLineItemQuantity) o;
        return quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
