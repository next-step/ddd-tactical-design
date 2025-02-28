package kitchenpos.eatinorders.tobe.domain.order;

import java.util.UUID;

public class DefaultEatInOrderTable implements EatInOrderTable {
    private final UUID id;
    private final boolean occupied;

    public DefaultEatInOrderTable(final UUID id, final boolean occupied) {
        this.id = id;
        this.occupied = occupied;
    }

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public boolean isOccupiedValue() {
        return occupied;
    }
}
