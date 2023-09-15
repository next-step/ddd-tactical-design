package kitchenpos.ordertables.dto;

public class ChangeNumberOfGuestRequest {
    private int numberOfGuest;

    public ChangeNumberOfGuestRequest(int numberOfGuest) {
        this.numberOfGuest = numberOfGuest;
    }

    public ChangeNumberOfGuestRequest() {
    }

    public int getNumberOfGuest() {
        return numberOfGuest;
    }

    public void setNumberOfGuest(int numberOfGuest) {
        this.numberOfGuest = numberOfGuest;
    }
}
