package kitchenpos.eatinorders.tobe.domain.model.orderstatus;

import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;

public class Served implements OrderStatus {

    private final String status = "Served";

    @Override
    public OrderStatus proceed() {
        return new Completed();
    }

    @Override
    public String getStatus() {
        return status;
    }
}
