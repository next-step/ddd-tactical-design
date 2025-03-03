package kitchenpos.order.common.domain.model;

import jakarta.persistence.Embeddable;
import kitchenpos.menu.domain.exception.MenuProductQtyException;

@Embeddable
public record OrderLineItemQty(long quantity) {

    public static OrderLineItemQty of(long quantity) {
        if (quantity < 0) {
            throw new MenuProductQtyException();
        }
        return new OrderLineItemQty(quantity);
    }

    public boolean isNegative() {
        return this.quantity < 0;
    }

    public long get() {
        return quantity;
    }
}

