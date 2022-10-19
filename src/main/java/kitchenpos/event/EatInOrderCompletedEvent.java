package kitchenpos.event;

import java.util.Objects;
import java.util.UUID;

public class EatInOrderCompletedEvent {

    private final UUID eatInOrderTableId;

    public EatInOrderCompletedEvent(UUID eatInOrderTableId) {
        this.eatInOrderTableId = eatInOrderTableId;
    }

    public UUID getEatInOrderTableId() {
        return eatInOrderTableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EatInOrderCompletedEvent that = (EatInOrderCompletedEvent) o;
        return Objects.equals(eatInOrderTableId, that.eatInOrderTableId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eatInOrderTableId);
    }
}
