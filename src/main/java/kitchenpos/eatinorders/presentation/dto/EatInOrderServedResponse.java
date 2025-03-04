package kitchenpos.eatinorders.presentation.dto;

import kitchenpos.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.EatInOrderLineItems;
import kitchenpos.eatinorders.tobe.domain.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.OrderTableId;
import kitchenpos.eatinorders.tobe.domain.common.OrderId;

import java.time.LocalDateTime;

public class EatInOrderServedResponse {

    private OrderId id;

    private EatInOrderStatus status;

    private EatInOrderLineItems orderLineItems;

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

    public EatInOrderServedResponse(OrderId id, EatInOrderStatus status, EatInOrderLineItems orderLineItems, OrderTableId orderTableId, LocalDateTime orderDateTime) {
        this.id = id;
        this.status = status;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
        this.orderDateTime = orderDateTime;
    }

    public OrderId getId() {
        return id;
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public EatInOrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public OrderTableId getOrderTableId() {
        return orderTableId;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }
}
