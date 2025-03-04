package kitchenpos.eatinorders.presentation.dto;

import kitchenpos.eatinorders.tobe.domain.OrderTableId;
import kitchenpos.eatinorders.tobe.domain.common.OrderEntity;
import kitchenpos.eatinorders.tobe.domain.common.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.common.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.common.OrderType;

public class EatInOrderCreateRequest {

    private OrderLineItems orderLineItems;

    private OrderTableId orderTableId;

    public static OrderEntity toEntity(EatInOrderCreateRequest request) {
        return new OrderEntity(
                OrderType.EAT_IN,
                OrderStatus.WAITING,
                request.getOrderLineItems(),
                null,
                request.getOrderTableId()
        );
    }

    public EatInOrderCreateRequest(OrderLineItems orderLineItems, OrderTableId orderTableId) {
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    public OrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public OrderTableId getOrderTableId() {
        return orderTableId;
    }
}
