package kitchenpos.eatinorders.tobe.domain.model.orderstatus;

import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;

public class Waiting implements OrderStatus {

    private final String status = "Waiting";

    @Override
    public OrderStatus proceed() {
        return new Accepted();
    }

    @Override
    public String getStatus() {
        return status;
    }
}
