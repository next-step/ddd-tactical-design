package kitchenpos.common.event;

import java.util.Objects;
import java.util.UUID;

public class LastEatInOrderCompleted {

    private final UUID orderTableId;

    public LastEatInOrderCompleted(UUID orderTableId) {
        this.orderTableId = orderTableId;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastEatInOrderCompleted that = (LastEatInOrderCompleted) o;
        return Objects.equals(orderTableId, that.orderTableId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderTableId);
    }
}
