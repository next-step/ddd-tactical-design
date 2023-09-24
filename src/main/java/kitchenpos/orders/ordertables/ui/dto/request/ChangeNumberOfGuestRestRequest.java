package kitchenpos.orders.ordertables.ui.dto.request;

public class ChangeNumberOfGuestRestRequest {
    final private int numberOfGuest;

    public ChangeNumberOfGuestRestRequest(int numberOfGuest) {
        this.numberOfGuest = numberOfGuest;
    }

    public int getNumberOfGuest() {
        return numberOfGuest;
    }

}
