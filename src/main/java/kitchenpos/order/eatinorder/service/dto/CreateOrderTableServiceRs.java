package kitchenpos.order.eatinorder.service.dto;

import java.util.UUID;
import kitchenpos.order.eatinorder.domain.model.OrderTable;

public class CreateOrderTableServiceRs {
    private UUID id;
    private String name;
    private int numberOfGuests;
    private boolean occupied;

    public CreateOrderTableServiceRs(UUID id, String name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public CreateOrderTableServiceRs(OrderTable orderTable) {
        this.id = orderTable.getId();
        this.name = orderTable.getInnerName();
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
