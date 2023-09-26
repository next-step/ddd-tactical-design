package kitchenpos.eatinorders.application.dto.request;

public class OrderTableChangeRequest {
    private int numberOfGuests;

    public OrderTableChangeRequest() {
    }

    public OrderTableChangeRequest(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}
