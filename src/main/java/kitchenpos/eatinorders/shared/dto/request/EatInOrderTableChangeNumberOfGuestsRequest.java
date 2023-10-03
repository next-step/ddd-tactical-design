package kitchenpos.eatinorders.shared.dto.request;

public class EatInOrderTableChangeNumberOfGuestsRequest {
    private int numberOfGuests;

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public EatInOrderTableChangeNumberOfGuestsRequest(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
}
