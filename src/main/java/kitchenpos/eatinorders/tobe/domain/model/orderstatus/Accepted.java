package kitchenpos.eatinorders.tobe.domain.model.orderstatus;

import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;

public class Accepted implements OrderStatus {

    private static final String status = "Accepted";

    @Override
    public OrderStatus proceed() {
        return new Served();
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }
}
