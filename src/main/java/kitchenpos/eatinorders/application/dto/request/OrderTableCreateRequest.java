package kitchenpos.eatinorders.application.dto.request;

public class OrderTableCreateRequest {
    private String name;
    private int numberOfGuests;

    public OrderTableCreateRequest() {
    }

    public OrderTableCreateRequest(String name, int numberOfGuests) {
        this.name = name;
        this.numberOfGuests = numberOfGuests;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}
