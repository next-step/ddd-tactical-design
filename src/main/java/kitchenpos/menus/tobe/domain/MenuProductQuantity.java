package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuProductQuantity {

    @Column(name = "quantity", nullable = false)
    private long quantity;

    public MenuProductQuantity(long quantity) {
        checkQuantityIsMinus(quantity);

        this.quantity = quantity;
    }

    protected MenuProductQuantity() {
    }

    private void checkQuantityIsMinus(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("메뉴에 등록될 상품의 개수는 음수일수 없습니다");
        }
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuProductQuantity)) {
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
