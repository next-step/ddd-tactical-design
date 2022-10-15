package kitchenpos.eatinorders.ui.request;

public class OrderTableChangeNumberOfGuestsRequest {

    private int numberOfGuests;

    protected OrderTableChangeNumberOfGuestsRequest() {
    }

    public OrderTableChangeNumberOfGuestsRequest(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}
