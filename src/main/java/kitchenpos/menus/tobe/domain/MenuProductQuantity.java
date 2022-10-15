package kitchenpos.menus.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuProductQuantity {
    private long quantity;

    protected MenuProductQuantity() {
    }

    public MenuProductQuantity(long quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    private void validateQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
    }

    public long quantity() {
        return quantity;
    }
}
