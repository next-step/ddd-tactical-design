package kitchenpos.orders.common.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.orders.common.domain.OrderType;

@Embeddable
public class MenuQuantity {

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected MenuQuantity() {
    }

    public MenuQuantity(OrderType type, long quantity) {
        if (type != OrderType.EAT_IN) {
            validateQuantity(quantity);
        }

        this.quantity = quantity;
    }

    private static void validateQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
    }
}
