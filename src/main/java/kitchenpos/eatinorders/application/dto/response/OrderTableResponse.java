package kitchenpos.eatinorders.application.dto.response;

import kitchenpos.eatinorders.domain.OrderTable;

import java.util.UUID;

public class OrderTableResponse {
    private UUID id;
    private String name;
    private int numberOfGuests;
    private boolean occupied;

    public OrderTableResponse() {
    }

    private OrderTableResponse(UUID id, String name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public static OrderTableResponse of(OrderTable orderTable) {
        return new OrderTableResponse(
            orderTable.getId(),
            orderTable.getName(),
            orderTable.getNumberOfGuests(),
            orderTable.isOccupied()
        );
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
