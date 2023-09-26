package kitchenpos.eatinorders.application.dto;

import kitchenpos.eatinorders.domain.ordertable.OrderTable;

import java.util.UUID;

public class OrderTableResponse {
    private final UUID id;

    private final String name;

    private final int numberOfGuests;

    private final boolean occupied;

    public OrderTableResponse(final OrderTable orderTable) {
        this.id = orderTable.getId();
        this.name = orderTable.getName();
        this.numberOfGuests = orderTable.getNumberOfGuests();
        this.occupied = orderTable.isOccupied();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }

}
