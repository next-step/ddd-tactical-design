package kitchenpos.eatinorders.ui.dto;

import kitchenpos.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.OrderTableId;
import kitchenpos.eatinorders.tobe.domain.common.OrderId;
import kitchenpos.eatinorders.tobe.domain.common.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.common.OrderStatus;

import java.time.LocalDateTime;

public class EatInOrderServedResponse {

    private OrderId id;

    private OrderStatus status;

    private OrderLineItems orderLineItems;

    private OrderTableId orderTableId;

    private LocalDateTime orderDateTime;

    public static EatInOrderServedResponse from(EatInOrder order) {
        return new EatInOrderServedResponse(
                order.getId(),
                order.getStatus(),
                order.getOrderLineItems(),
                order.getOrderTableId(),
                order.getOrderDateTime()
        );
    }

    public EatInOrderServedResponse(OrderId id, OrderStatus status, OrderLineItems orderLineItems, OrderTableId orderTableId, LocalDateTime orderDateTime) {
        this.id = id;
        this.status = status;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
        this.orderDateTime = orderDateTime;
    }

    public OrderId getId() {
        return id;
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
