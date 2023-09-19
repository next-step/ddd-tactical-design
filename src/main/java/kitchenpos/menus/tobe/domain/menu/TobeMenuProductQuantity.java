package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
public class TobeMenuProductQuantity {
    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected TobeMenuProductQuantity() {
    }

    public TobeMenuProductQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("메뉴 상품 수량은 0보다 작을 수 없습니다. 수량: " + quantity);
        }

        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TobeMenuProductQuantity quantity1 = (TobeMenuProductQuantity) o;
        return quantity == quantity1.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
