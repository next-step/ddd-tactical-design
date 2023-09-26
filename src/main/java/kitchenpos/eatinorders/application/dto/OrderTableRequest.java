package kitchenpos.eatinorders.application.dto;

public class OrderTableRequest {
    private final String name;

    private final boolean occupied;

    private final int numberOfGuests;

    public OrderTableRequest(final String name, final boolean occupied, final int numberOfGuests) {
        this.name = name;
        this.occupied = occupied;
        this.numberOfGuests = numberOfGuests;
    }

    public OrderTableRequest(final String name) {
        this(name, false, 0);
    }

    public OrderTableRequest(final int numberOfGuests) {
        this(null, false, numberOfGuests);
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
