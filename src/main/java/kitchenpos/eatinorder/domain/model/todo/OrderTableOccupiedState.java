package kitchenpos.eatinorder.domain.model.todo;

import java.util.Objects;

public class OrderTableOccupiedState {
    public static final OrderTableOccupiedState USED = new OrderTableOccupiedState(true);
    public static final OrderTableOccupiedState EMPTY = new OrderTableOccupiedState(false);
    private final boolean occupied;

    private OrderTableOccupiedState(final boolean occupied) {
        this.occupied = occupied;
    }

    public static OrderTableOccupiedState of(final boolean occupied) {
        if (occupied) {
            return USED;
        }
        return EMPTY;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean isVacant() {
        return !isOccupied();
    }

    public OrderTableOccupiedState occupy() {
        return USED;
    }

    public OrderTableOccupiedState vacate() {
        return EMPTY;
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
