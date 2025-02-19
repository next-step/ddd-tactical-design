package kitchenpos.menu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

import static kitchenpos.menu.exception.MenuExceptionMessage.MENU_PRODUCT_QUANTITY_CREATION_EXCEPTION;

@Embeddable
public class MenuProductQuantity {
    @Column(name = "quantity", nullable = false)
    private final Long value;

    public MenuProductQuantity(long value) {
        validateMenuProductQuantity(value);
        this.value = value;
    }

    private void validateMenuProductQuantity(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(MENU_PRODUCT_QUANTITY_CREATION_EXCEPTION.getMessage());
        }
    }

    protected MenuProductQuantity() {
        this.value = null;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuProductQuantity that = (MenuProductQuantity) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
