package kitchenpos.eatinorders.application.dto;

import java.util.UUID;

public class OrderTableResponse {
    private final UUID id;
    private final String name;
    private final int numberOfGuests;
    private final boolean occupied;

    private OrderTableResponse(UUID id, String name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public static OrderTableResponse create(UUID id, String name, int numberOfGuests, boolean occupied) {
        return new OrderTableResponse(id, name, numberOfGuests, occupied);
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
