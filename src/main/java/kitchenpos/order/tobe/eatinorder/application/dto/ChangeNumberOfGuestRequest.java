package kitchenpos.order.tobe.eatinorder.application.dto;

public class ChangeNumberOfGuestRequest {
    private final int numberOfGuests;

    public ChangeNumberOfGuestRequest(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}
