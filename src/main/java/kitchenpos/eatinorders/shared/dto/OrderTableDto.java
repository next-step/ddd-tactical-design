package kitchenpos.eatinorders.shared.dto;

import java.util.UUID;

public class OrderTableDto {
    private UUID id;
    private String name;
    private int numberOfGuests;
    private boolean occupied;
    protected OrderTableDto() {
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
