package kitchenpos.menus.vo;

import kitchenpos.menus.tobe.domain.exception.InvalidMenuProductQuantityException;

public class MenuProductQuantity {
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
