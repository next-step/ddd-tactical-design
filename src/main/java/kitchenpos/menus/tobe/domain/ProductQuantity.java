package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductQuantity {
    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected ProductQuantity() {}

    public ProductQuantity(final long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("주문 항목의 수량은 0 미만일 수 없다.");
        }
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }
}
