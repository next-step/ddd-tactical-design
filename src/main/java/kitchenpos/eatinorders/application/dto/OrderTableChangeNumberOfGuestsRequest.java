package kitchenpos.eatinorders.application.dto;

public class OrderTableChangeNumberOfGuestsRequest {
    private Integer numberOfGuests;

    private OrderTableChangeNumberOfGuestsRequest(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public static OrderTableChangeNumberOfGuestsRequest create(Integer numberOfGuests) {
        return new OrderTableChangeNumberOfGuestsRequest(numberOfGuests);
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }
}
