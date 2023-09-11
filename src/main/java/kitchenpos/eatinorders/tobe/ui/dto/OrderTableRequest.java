package kitchenpos.eatinorders.tobe.ui.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class OrderTableRequest {

    @NotEmpty
    private final String name;

    @Min(0)
    private final int numberOfGuests;

    private final boolean occupied;

    public OrderTableRequest(String name, int numberOfGuests, boolean occupied) {
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
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
