package kitchenpos.menu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class MenuProductQuantity {
    private static final String MENU_PRODUCT_QUANTITY_CREATION_EXCEPTION = "메뉴 상품의 수량은 0보다 커야 합니다!";

    @Column(name = "quantity", nullable = false)
    private final Long value;

    public MenuProductQuantity(long value) {
        validateMenuProductQuantity(value);
        this.value = value;
    }

    private void validateMenuProductQuantity(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(MENU_PRODUCT_QUANTITY_CREATION_EXCEPTION);
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
