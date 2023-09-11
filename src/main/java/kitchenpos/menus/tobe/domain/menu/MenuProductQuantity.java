package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuProductQuantityException;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class MenuProductQuantity {
    @Column(name = "quantity", nullable = false)
    private long value;

    protected MenuProductQuantity() {

    }

    public MenuProductQuantity(long value) {
        if (isLessThanZero(value)) {
            throw new MenuProductQuantityException(MenuErrorCode.QUANTITY_IS_GREATER_THAN_ZERO);
        }
        this.value = value;
    }

    private boolean isLessThanZero(long input) {
        return input < 0;
    }


    public long getValue() {
        return value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuProductQuantity that = (MenuProductQuantity) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }
}


