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
        validationOfQuantity(quantity, orderType);
        return new EatInOrderQuantity(quantity);
    }

    private static void validationOfQuantity(long quantity, kitchenpos.eatinorders.domain.OrderType orderType) {
        if (orderType == OrderType.EAT_IN) {
            return;
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("수량은 0보다 작을 수 없습니다.");
        }
    }

    public long value() {
        return quantity;
    }
}
