package kitchenpos.eatinorders.ordertable.application.dto;

import java.util.UUID;

import kitchenpos.eatinorders.ordertable.domain.OrderTable;

public class OrderTableResponse {
    private UUID id;
    private String name;
    private int numberOfGuests;
    private boolean occupied;

    public OrderTableResponse(UUID id, String name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public OrderTableResponse(OrderTable orderTable) {
        this(orderTable.getId(), orderTable.getName(), orderTable.getNumberOfGuests().intValue(), orderTable.isInUse());
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
