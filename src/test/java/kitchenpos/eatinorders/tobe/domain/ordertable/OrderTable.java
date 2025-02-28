package kitchenpos.eatinorders.tobe.domain.ordertable;

import kitchenpos.eatinorders.tobe.domain.ordertable.vo.NumberOfGuests;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.Occupied;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableName;

import java.util.UUID;

public class OrderTable {
    private final OrderTableId id;
    private final OrderTableName name;
    private NumberOfGuests numberOfGuests;
    private Occupied occupied;

    public OrderTable(final String name, final int numberOfGuests, final boolean occupied) {
        this(new OrderTableId(), new OrderTableName(name), new NumberOfGuests(numberOfGuests), new Occupied(occupied));
    }

    public OrderTable(final OrderTableId id, final OrderTableName name, final NumberOfGuests numberOfGuests, final Occupied occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public void sit() {
        this.occupied = new Occupied(true);
    }

    public boolean isOccupiedValue() {
        return occupied.getValue();
    }

    public void changeNumberOfGuests(final int numberOfGuests) {
        if (!occupied.getValue()) {
            throw new IllegalArgumentException();
        }
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }

    public OrderTableId id() {
        return id;
    }

    public UUID idValue() {
        return id.getValue();
    }

    public int numberOfGuests() {
        return numberOfGuests.getValue();
    }
}
