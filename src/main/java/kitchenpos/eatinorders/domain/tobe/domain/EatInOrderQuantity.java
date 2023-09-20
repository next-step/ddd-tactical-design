package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import kitchenpos.eatinorders.domain.OrderType;

@Embeddable
public class EatInOrderQuantity {
    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected EatInOrderQuantity() {
    }

    private EatInOrderQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EatInOrderQuantity quantity1 = (EatInOrderQuantity)o;
        return quantity == quantity1.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }

    public static EatInOrderQuantity of(long quantity, OrderType orderType) {
        return new EatInOrderQuantity(quantity);
    }

    public long value() {
        return quantity;
    }
}
