package kitchenpos.eatinorders.application.dto;

import kitchenpos.eatinorders.domain.eatinorder.OrderTable;

import java.util.UUID;

public class OrderTableRequest {

    private UUID id;

    private String name;

    private int numberOfGuests;

    private boolean occupied;

    public OrderTableRequest() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(final int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(final boolean occupied) {
        this.occupied = occupied;
    }

    public OrderTable to(){
        return OrderTable.of(name, numberOfGuests);
    }
}
