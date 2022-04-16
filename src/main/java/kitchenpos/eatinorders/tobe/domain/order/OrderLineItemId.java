package kitchenpos.eatinorders.tobe.domain.order;

import java.util.Objects;

public final class OrderLineItemId {

    private final long seq;

    public OrderLineItemId(long seq) {
        this.seq = seq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderLineItemId)) {
            return false;
        }
        OrderLineItemId that = (OrderLineItemId) o;
        return seq == that.seq;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

    @Override
    public String toString() {
        return "OrderLineItemId{" +
            "seq=" + seq +
            '}';
    }
}
