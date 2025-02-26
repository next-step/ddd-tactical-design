package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.vo.OrderTableName;

import java.util.UUID;

public class OrderTable {
    private UUID id;
    private OrderTableName name;
    private int numberOfGuests;
    private boolean occupied;

    public OrderTable(final UUID id, final String name, final int numberOfGuests, final boolean occupied) {
        this(id, new OrderTableName(name), numberOfGuests, occupied);
    }

    public OrderTable(final UUID id, final OrderTableName name, final int numberOfGuests, final boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }
}
