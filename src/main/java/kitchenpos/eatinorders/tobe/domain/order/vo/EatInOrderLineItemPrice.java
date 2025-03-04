package kitchenpos.eatinorders.tobe.domain.order.vo;

import java.util.Objects;

public class EatInOrderLineItemPrice {
    private final int value;

    public EatInOrderLineItemPrice(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EatInOrderLineItemPrice that = (EatInOrderLineItemPrice) o;
        return getValue() == that.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
