package kitchenpos.eatinorders.tobe.ui.dto;

public class OrderTableResponse {

    private final String id;

    private final String name;

    private final int numberOfGuests;

    private final boolean occupied;


    public OrderTableResponse(String id, String name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public String getId() {
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
