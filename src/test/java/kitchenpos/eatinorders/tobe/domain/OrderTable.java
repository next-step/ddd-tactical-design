package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.vo.NumberOfGuests;
import kitchenpos.eatinorders.tobe.domain.vo.OrderTableName;

import java.util.UUID;

public class OrderTable {
    private final UUID id;
    private final OrderTableName name;
    private NumberOfGuests numberOfGuests;
    private boolean occupied;

    public OrderTable(final UUID id, final String name, final int numberOfGuests, final boolean occupied) {
        this(id, new OrderTableName(name), new NumberOfGuests(numberOfGuests), occupied);
    }

    public OrderTable(final UUID id, final OrderTableName name, final NumberOfGuests numberOfGuests, final boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public void sit() {
        this.occupied = true;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void changeNumberOfGuests(final int numberOfGuests) {
        if (!occupied) {
            throw new IllegalArgumentException();
        }
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }
}
