package kitchenpos.eatinorder.domain.model.todo;

import java.util.Objects;

public class OrderTableOccupiedState {
    private static final OrderTableOccupiedState OCCUPIED = new OrderTableOccupiedState(true);
    private static final OrderTableOccupiedState VACANT = new OrderTableOccupiedState(false);
    private final boolean occupied;

    private OrderTableOccupiedState(final boolean occupied) {
        this.occupied = occupied;
    }

    public static OrderTableOccupiedState of(final boolean occupied) {
        if (occupied) {
            return OCCUPIED;
        }
        return VACANT;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public OrderTableOccupiedState occupy() {
        return OCCUPIED;
    }

    public OrderTableOccupiedState vacate() {
        return VACANT;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderTableOccupiedState that = (OrderTableOccupiedState) o;
        return occupied == that.occupied;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(occupied);
    }
}
