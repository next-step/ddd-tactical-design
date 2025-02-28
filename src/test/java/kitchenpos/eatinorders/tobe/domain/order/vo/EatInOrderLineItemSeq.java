package kitchenpos.eatinorders.tobe.domain.order.vo;

import java.util.Objects;

public class EatInOrderLineItemSeq {
    private final long value;

    public EatInOrderLineItemSeq(final long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EatInOrderLineItemSeq that = (EatInOrderLineItemSeq) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
