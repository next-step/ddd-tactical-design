package kitchenpos.eatinorders.tobe.domain.order.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class EatInOrderLineItemQuantity {

    @Column(name = "quantity", nullable = false)
    private int quantity;

    protected EatInOrderLineItemQuantity() {
    }

    public EatInOrderLineItemQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EatInOrderLineItemQuantity that)) return false;
        return getQuantity() == that.getQuantity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuantity());
    }
}
