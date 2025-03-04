package kitchenpos.eatinorders.presentation.dto;

import kitchenpos.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.EatInOrderLineItems;
import kitchenpos.eatinorders.tobe.domain.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.OrderTableId;
import kitchenpos.eatinorders.tobe.domain.common.OrderId;
import kitchenpos.eatinorders.tobe.domain.common.OrderType;

import java.time.LocalDateTime;

public class EatInOrderCreateResponse {
    private OrderId id;

    private OrderType orderType;

    private EatInOrderStatus status;

    private EatInOrderLineItems orderLineItems;

    private OrderTableId orderTableId;

    private LocalDateTime orderDateTime;

    public static EatInOrderCreateResponse from(EatInOrder order) {
        return new EatInOrderCreateResponse(
                order.getId(),
                order.getType(),
                order.getStatus(),
                order.getOrderLineItems(),
                order.getOrderTableId(),
                order.getOrderDateTime()
        );
    }

    public EatInOrderCreateResponse(OrderId id, OrderType orderType, EatInOrderStatus status, EatInOrderLineItems orderLineItems, OrderTableId orderTableId, LocalDateTime orderDateTime) {
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
