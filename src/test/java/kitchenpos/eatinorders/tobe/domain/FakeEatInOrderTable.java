package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;

public class FakeEatInOrderTable implements EatInOrderTable{

    private final UUID orderTableId;
    private final boolean isOccupied;

    public FakeEatInOrderTable() {
        this.orderTableId = UUID.randomUUID();
        this.isOccupied = false;
    }

    public FakeEatInOrderTable(final UUID orderTableId, final boolean isOccupied) {
        this.orderTableId = orderTableId;
        this.isOccupied = isOccupied;
    }

    @Override
    public UUID id() {
        return orderTableId;
    }

    @Override
    public boolean isOccupiedValue() {
        return isOccupied;
    }
}
