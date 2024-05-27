package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Quantity {
    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected Quantity() {}

    public Quantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
    }

    public long getQuantity() {
        return quantity;
    }
}
