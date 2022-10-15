package kitchenpos.eatinorders.ui.request;

public class EatInOrderTableChangeNumberOfGuestsRequest {

    private int numberOfGuests;

    protected EatInOrderTableChangeNumberOfGuestsRequest() {
    }

    public EatInOrderTableChangeNumberOfGuestsRequest(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}
