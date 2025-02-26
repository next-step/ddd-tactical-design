package kitchenpos.menu.domain.model;

import jakarta.persistence.Embeddable;
import kitchenpos.menu.domain.exception.MenuProductQtyException;

@Embeddable
public record MenuProductQty(long quantity) {

    public static MenuProductQty of(long quantity) {
        if (quantity < 0) {
            throw new MenuProductQtyException();
        }
        return new MenuProductQty(quantity);
    }

    public boolean isNegative() {
        return this.quantity < 0;
    }

    public long get() {
        return quantity;
    }
}

