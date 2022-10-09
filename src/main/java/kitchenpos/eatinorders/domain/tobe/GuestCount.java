package kitchenpos.eatinorders.domain.tobe;

public class GuestCount {
    private final int numberOfGuests;

    public GuestCount(final int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException("numberOfGuest must bigger than or equals 0");
        }
        this.numberOfGuests = numberOfGuests;
    }

    public static GuestCount zeroGuestCount() {
        GuestCount guestCount = new GuestCount(0);
        return guestCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GuestCount that = (GuestCount) o;

        return numberOfGuests == that.numberOfGuests;
    }

    @Override
    public int hashCode() {
        return numberOfGuests;
    }
}
