package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Quantity {
    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected Quantity() {}

    public Quantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("상품의 수량은 0개 이상이어야 합니다.");
        }
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }
}
