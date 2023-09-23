package kitchenpos.orders.ordertables.ui.dto.response;

import java.util.UUID;

public class OrderTableRestResponse {

    private UUID id;
    private String name;
    private int numberOfGuest;
    private boolean occupied;

    public OrderTableRestResponse() {
    }

    public OrderTableRestResponse(UUID orderTableId, String name, int numberOfGuest, boolean occupied) {
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
