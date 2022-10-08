package kitchenpos.menus.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.menus.exception.InvalidMenuProductQuantityException;

@Embeddable
public class MenuProductQuantity {

    @Column(name = "quantity", nullable = false)
    private long value;

    protected MenuProductQuantity() {
    }

    public MenuProductQuantity(long value) {
        this.value = value;
        validateNegative(this.value);
    }

    private void validateNegative(long value) {
        if (value < 0) {
            throw new InvalidMenuProductQuantityException("메뉴의 상품 수량은 0 이상어야 합니다.");
        }
    }

    public long getValue() {
        return value;
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
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
