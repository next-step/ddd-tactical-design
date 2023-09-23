package kitchenpos.orders.ordertables.application.dto;

import java.util.UUID;

public class OrderTableResponse {

    private UUID id;
    private String name;
    private int numberOfGuest;
    private boolean occupied;

    public OrderTableResponse() {
    }

    public OrderTableResponse(UUID orderTableId, String name, int numberOfGuest, boolean occupied) {
        this.id = orderTableId;
        this.name = name;
        this.numberOfGuest = numberOfGuest;
        this.occupied = occupied;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfGuest() {
        return numberOfGuest;
    }

    public UUID getId() {
        return id;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
