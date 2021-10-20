package kitchenpos.eatinorders.tobe.ordertable.ui.dto;

public class ChangeNumberOfGuestsRequest {
    private final int numberOfGuests;

    public ChangeNumberOfGuestsRequest(final int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}
