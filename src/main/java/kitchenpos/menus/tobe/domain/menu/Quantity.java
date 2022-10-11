package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Quantity {

    private BigDecimal quantity;

    protected Quantity() {}

    public Quantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal quantity() {
        return this.quantity;
    }

    public boolean greaterThanZero() {
        return this.quantity.compareTo(BigDecimal.ZERO) > 0;
    }
}
