package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Completed;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {

    private final UUID id;

    private final UUID orderTableId;

    private OrderStatus orderStatus;

    private final OrderLineItems orderLineItems;

    private final LocalDateTime orderDateTime;

    public Order(
            final UUID id,
            final UUID orderTableId,
            final OrderStatus orderStatus,
            final OrderLineItems orderLineItems,
            final Validator<Order> validator
    ) {
        this.id = id;
        this.orderTableId = orderTableId;
        this.orderStatus = orderStatus;
        this.orderLineItems = orderLineItems;
        this.orderDateTime = LocalDateTime.now();

        validator.validate(this);
    }

    public void proceed() {
        orderStatus = orderStatus.proceed();
    }

    public boolean isCompleted() {
        return orderStatus instanceof Completed;
    }

    public UUID getId() {
        return id;
    }

    public UUID getTableId() {
        return orderTableId;
    }

    public String getStatus() {
        return orderStatus.getStatus();
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }
}
