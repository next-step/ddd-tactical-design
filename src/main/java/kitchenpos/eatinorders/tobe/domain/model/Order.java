package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.service.Validator;

import java.util.UUID;

public class Order {

    public Order(
            final UUID id,
            final UUID orderTableId,
            final OrderStatus orderStatus,
            final OrderLineItems orderLineItems,
            final Validator<Order> validator
    ) {
    }

    public void proceed() {
    }

    public boolean isCompleted() {
        return false;
    }

    public UUID getId() {
        return null;
    }

    public UUID getTableId() {
        return null;
    }

    public String getStatus() {
        return null;
    }
}
