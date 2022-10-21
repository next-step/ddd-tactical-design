package kitchenpos.common.event;

import java.util.Objects;
import java.util.UUID;

public class LastEatInOrderCompletedEvent {

    private final UUID orderTableId;

    public LastEatInOrderCompletedEvent(UUID orderTableId) {
        this.orderTableId = orderTableId;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastEatInOrderCompletedEvent that = (LastEatInOrderCompletedEvent) o;
        return Objects.equals(orderTableId, that.orderTableId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderTableId);
    }
}
