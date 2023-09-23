package kitchenpos.orders.ordertables.dto;

import kitchenpos.orders.ordertables.domain.OrderTable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
