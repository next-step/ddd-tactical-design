package kitchenpos.eatinorders.domain.tobe;

public class OrderTable {
    private OrderTableId id;
    private OrderTableName name;
    private GuestCount guestCount;
    private boolean occupied;

    public OrderTable(final OrderTableId id, final OrderTableName name) {
        this.id = id;
        this.name = name;
        this.guestCount = GuestCount.zeroGuestCount();
        this.occupied = false;
    }

    public void clear() {
        this.guestCount = GuestCount.zeroGuestCount();
        this.occupied = false;
    }

    public void occupy() {
        this.occupied = true;
    }

    public void changeGuest(final GuestCount guestCount) {
        if (this.occupied == false) {
            throw new IllegalStateException("if orderTable is empty, it is not possible");
        }
        this.guestCount = guestCount;
    }

    public boolean isOccupied() {
        return this.occupied;
    }

    public GuestCount guestCount() {
        return this.guestCount;
    }

    public OrderTableId getId() {
        return this.id;
    }
}
