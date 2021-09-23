package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.service.Policy;
import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Completed;
import kitchenpos.menus.tobe.domain.model.Menu;

import java.time.LocalDateTime;
import java.util.List;
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
            final Validator<Order> orderCreateValidator
    ) {
        this.id = id;
        this.orderTableId = orderTableId;
        this.orderStatus = orderStatus;
        this.orderLineItems = orderLineItems;
        this.orderDateTime = LocalDateTime.now();

        orderCreateValidator.validate(this);
    }

    public void proceed(final Policy<Order> orderCompletePolicy) {
        orderStatus = orderStatus.proceed();

        orderCompletePolicy.enforce(this);
    }

    public void validateOrderPrice(final List<Menu> menus) {
        orderLineItems.validateOrderPrice(menus);
    }

    public boolean isCompleted() {
        return orderStatus.equals(new Completed());
    }

    public UUID getId() {
        return id;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public String getStatus() {
        return orderStatus.getStatus();
    }

    public List<UUID> getMenuIds() {
        return orderLineItems.getMenuIds();
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }
}
