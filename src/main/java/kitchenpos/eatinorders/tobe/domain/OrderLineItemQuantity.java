package kitchenpos.eatinorders.tobe.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderLineItemQuantity {

    public static OrderLineItemQuantity INIT = new OrderLineItemQuantity(0);

    @Column(name = "quantity", nullable = false)
    private long quantity;

    public OrderLineItemQuantity(long quantity) {
        this.quantity = quantity;
    }

    protected OrderLineItemQuantity() {
    }

    public long getQuantity() {
        return this.quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderLineItemQuantity)) {
            return false;
        }
        OrderLineItemQuantity that = (OrderLineItemQuantity) o;
        return quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
