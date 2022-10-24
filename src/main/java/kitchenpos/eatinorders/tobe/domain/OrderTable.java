package kitchenpos.eatinorders.tobe.domain;

public class OrderTable {
    private int numberOfGuests;
    private boolean occupied;

    public OrderTable(final int numberOfGuests, final boolean occupied) {
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void makeEmpty() {
        numberOfGuests = 0;
        occupied = false;
    }
}
