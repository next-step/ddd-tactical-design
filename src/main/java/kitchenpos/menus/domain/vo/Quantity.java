package kitchenpos.menus.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Quantity {

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected Quantity() {
    }

    public Quantity(long quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    private void validateQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
    }

    public long getValue() {
        return quantity;
    }
}
