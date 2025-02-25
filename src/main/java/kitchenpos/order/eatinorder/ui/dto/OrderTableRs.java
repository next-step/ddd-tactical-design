package kitchenpos.order.eatinorder.ui.dto;

import java.util.UUID;
import kitchenpos.order.eatinorder.service.dto.OrderTableServiceRs;

public class OrderTableRs {
    private UUID id;
    private String name;
    private int numberOfGuests;
    private boolean occupied;

    public OrderTableRs(UUID id, String name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public OrderTableRs(OrderTableServiceRs rs) {
        this.id = rs.getId();
        this.name = rs.getName();
        this.numberOfGuests = rs.getNumberOfGuests();
        this.occupied = rs.isOccupied();
    }

    public OrderTableRs() {
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
