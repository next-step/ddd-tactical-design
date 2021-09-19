package kitchenpos.eatinorders.tobe.domain.model.orderstatus;

import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;

public class Accepted implements OrderStatus {

    private final String status = "Accepted";

    @Override
    public OrderStatus proceed() {
        return new Served();
    }

    @Override
    public String getStatus() {
        return status;
    }
}
