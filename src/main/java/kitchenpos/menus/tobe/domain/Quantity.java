package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;

public class Quantity {
    private final BigDecimal quantity;

    public Quantity(BigDecimal quantity) {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

}
