package kitchenpos.eatinorders.tobe.ui.dto;

import kitchenpos.eatinorders.tobe.domain.OrderTable;

public class OrderTableResponse {

    private final String id;

    private final String name;

    private final int numberOfGuests;

    private final boolean occupied;


    public OrderTableResponse(OrderTable orderTable) {
        this.id = orderTable.getId();
        this.name = orderTable.getName();
        this.numberOfGuests = orderTable.getNumberOfGuests();
        this.occupied = orderTable.isOccupied();
    }

    public String getId() {
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
