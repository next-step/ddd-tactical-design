package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.service.Validator;
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

    public void proceed() {
        orderStatus = orderStatus.proceed();
    }

    public List<UUID> getMenuIds() {
        return orderLineItems.getMenuIds();
    }

    public void validateOrderPrice(final List<Menu> menus) {
        orderLineItems.validateOrderPrice(menus);
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
