package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.service.Policy;
import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Accepted;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Completed;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Served;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Waiting;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Order {

    private final UUID id;

    private final UUID orderTableId;

    private OrderStatus orderStatus;

    private final OrderLineItems orderLineItems;

    private final LocalDateTime orderDateTime;

    private Order(
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

    public static Order create(
            final UUID id,
            final UUID orderTableId,
            final OrderLineItems orderLineItems,
            final Validator<Order> orderCreateValidator
    ) {
        return new Order(id, orderTableId, new Waiting(), orderLineItems, orderCreateValidator);
    }

    private void proceed() {
        orderStatus = orderStatus.proceed();
    }

    public void accept() {
        if (!new Waiting().equals(orderStatus)) {
            throw new IllegalStateException("주문이 접수 대기 상태가 아닙니다.");
        }

        proceed();
    }

    public void serve() {
        if (!new Accepted().equals(orderStatus)) {
            throw new IllegalStateException("주문이 접수 상태가 아닙니다.");
        }

        proceed();
    }

    public void complete(final Policy<Order> orderCompletePolicy) {
        if (!new Served().equals(orderStatus)) {
            throw new IllegalStateException("주문이 서빙 상태가 아닙니다.");
        }

        if (!orderLineItems.canBeCompleted()) {
            throw new IllegalStateException("계산 가능하지 않은 주문 항목이 있습니다.");
        }

        proceed();
        orderCompletePolicy.enforce(this);
    }

    public void validateOrderPrice(final Map<UUID, Price> menuPriceMap) {
        orderLineItems.validateOrderPrice(menuPriceMap);
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
