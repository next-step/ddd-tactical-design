package kitchenpos.menus.domain.vo;

import kitchenpos.menus.domain.exception.InvalidMenuProductQuantityException;
import kitchenpos.support.ValueObject;

public class MenuProductQuantity extends ValueObject {
    private final long quantity;

    public MenuProductQuantity(long quantity) {
        if (quantity < 0) {
            throw new InvalidMenuProductQuantityException();
        }
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }
}
