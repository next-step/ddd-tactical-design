package kitchenpos.menus.tobe.domain.menuproduct;

import kitchenpos.products.tobe.domain.TobeProduct;

import javax.persistence.*;
import java.util.UUID;

@Embeddable
public class MenuProductQuantity {
    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected MenuProductQuantity() {
    }

    public MenuProductQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("메뉴 상품 수량은 0보다 작을 수 없습니다. 수량: " + quantity);
        }

        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }
}
