package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MenuProductQuantity {

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected MenuProductQuantity() {
    }

    public MenuProductQuantity(final long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("메뉴 상품 수량은 0보다 작을 수 없습니다.");
        }
        this.quantity = quantity;
    }

    public long value() {
        return quantity;
    }
}
