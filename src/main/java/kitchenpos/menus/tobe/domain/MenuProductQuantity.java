package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuException;

public class MenuProductQuantity {

    private long quantity;

    protected MenuProductQuantity() {
        this.quantity = 0;
    }

    public MenuProductQuantity(long quantity) {
        if (isNegative(quantity)) {
            throw new MenuException(MenuErrorCode.MENU_PRODUCT_QUANTITY_NEGATIVE);
        }
        this.quantity = quantity;
    }

    public boolean isNegative(long quantity) {
        return quantity < 0;
    }

    public long getValue() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuProductQuantity that = (MenuProductQuantity) o;
        return quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
