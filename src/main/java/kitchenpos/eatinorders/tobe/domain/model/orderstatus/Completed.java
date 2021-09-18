package kitchenpos.eatinorders.tobe.domain.model.orderstatus;

import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;

public class Completed implements OrderStatus {

    @Override
    public OrderStatus proceed(final Order order) throws IllegalStateException {
        return null;
    }

    @Override
    public String getStatus() {
        return null;
    }
}
