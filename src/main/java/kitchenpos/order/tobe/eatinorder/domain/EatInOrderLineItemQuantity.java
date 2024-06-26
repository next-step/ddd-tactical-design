package kitchenpos.order.tobe.eatinorder.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EatInOrderLineItemQuantity {
    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected EatInOrderLineItemQuantity() {
    }

    public EatInOrderLineItemQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }
}
