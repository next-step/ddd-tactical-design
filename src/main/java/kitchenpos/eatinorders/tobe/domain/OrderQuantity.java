package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderQuantity {
    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected OrderQuantity() {}

    public OrderQuantity(final long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("주문은 수량이 1개 이상이어야 합니다.");
        }
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }
}
