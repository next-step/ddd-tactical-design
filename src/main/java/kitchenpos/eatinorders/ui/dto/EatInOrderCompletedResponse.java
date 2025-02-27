package kitchenpos.eatinorders.ui.dto;

import kitchenpos.eatinorders.tobe.domain.OrderTableId;
import kitchenpos.eatinorders.tobe.domain.common.*;

import java.time.LocalDateTime;

public class EatInOrderCompletedResponse {

    private OrderId id;

    private OrderType orderType;

    private OrderStatus status;

    private OrderLineItems orderLineItems;

    private OrderTableId orderTableId;

    private LocalDateTime orderDateTime;

    public static EatInOrderCompletedResponse from(OrderEntity entity) {
        return new EatInOrderCompletedResponse(
                entity.id(),
                entity.type(),
                entity.status(),
                entity.orderLineItems(),
                entity.orderTableId(),
                entity.orderDateTime()
        );
    }

    public EatInOrderCompletedResponse(OrderId id, OrderType orderType, OrderStatus status, OrderLineItems orderLineItems, OrderTableId orderTableId, LocalDateTime orderDateTime) {
        this.id = id;
        this.orderType = orderType;
        this.status = status;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
        this.orderDateTime = orderDateTime;
    }

    public OrderId getId() {
        return id;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public OrderTableId getOrderTableId() {
        return orderTableId;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }
}
