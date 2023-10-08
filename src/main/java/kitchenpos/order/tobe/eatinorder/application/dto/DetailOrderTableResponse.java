package kitchenpos.order.tobe.eatinorder.application.dto;

import java.util.UUID;
import kitchenpos.order.tobe.eatinorder.domain.OrderTable;

public class DetailOrderTableResponse {
    private final UUID id;
    private final String name;
    private final int numberOfGuests;
    private final boolean occupied;

    public DetailOrderTableResponse(UUID id, String name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public static DetailOrderTableResponse of(OrderTable entity) {
        return new DetailOrderTableResponse(entity.getId(), entity.getName(), entity.getNumberOfGuests(), entity.isOccupied());
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
