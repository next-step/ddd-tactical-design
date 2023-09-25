package kitchenpos.eatinorders.application.dto;

public class OrderTableClearResponse {
    private Integer numberOfGuests;
    private boolean occupied;

    private OrderTableClearResponse(Integer numberOfGuests, boolean occupied) {
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public static OrderTableClearResponse create(Integer numberOfGuests, boolean occupied) {
        return new OrderTableClearResponse(numberOfGuests, occupied);
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
