package kitchenpos.eatinorders.application.request;

import javax.validation.constraints.NotNull;

public class OrderTableChangeGuestsRequest {

    @NotNull
    private Integer guests;

    public OrderTableChangeGuestsRequest() {
    }

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }
}
