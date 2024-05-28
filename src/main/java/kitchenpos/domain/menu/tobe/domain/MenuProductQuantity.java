package kitchenpos.domain.menu.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
class MenuProductQuantity {
    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected MenuProductQuantity() {
    }

    public MenuProductQuantity(long quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    private void validateQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity는 음수일 수 없습니다.");
        }
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProductQuantity that = (MenuProductQuantity) o;
        return getQuantity() == that.getQuantity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuantity());
    }
}
