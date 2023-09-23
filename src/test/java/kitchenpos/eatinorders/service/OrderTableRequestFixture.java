package kitchenpos.eatinorders.service;

import kitchenpos.eatinorders.application.dto.ChangeNumberOfGuestsRequest;
import kitchenpos.eatinorders.application.dto.CreateOrderTableRequest;

public class OrderTableRequestFixture {
    private String name;
    private int numberOfGuests;
    
    public OrderTableRequestFixture() {
        name = "1번 테이블";
        numberOfGuests = 1;
    }

    public static OrderTableRequestFixture builder() {
        return new OrderTableRequestFixture();
    }

    public OrderTableRequestFixture name(String name) {
        this.name = name;
        return this;
    }

    public OrderTableRequestFixture numberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
        return this;
    }

    public CreateOrderTableRequest buildCreateRequest() {
        return new CreateOrderTableRequest(name);
    }

    public ChangeNumberOfGuestsRequest buildChangeRequest() {
        return new ChangeNumberOfGuestsRequest(numberOfGuests);
    }
}
