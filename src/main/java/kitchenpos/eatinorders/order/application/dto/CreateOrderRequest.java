package kitchenpos.eatinorders.order.application.dto;

import java.util.List;
import java.util.UUID;

import kitchenpos.eatinorders.order.domain.vo.OrderLineItemVo;

public class CreateOrderRequest {
    private UUID orderTableId;
    private List<OrderLineItemVo> orderLineItems;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(UUID orderTableId, List<OrderLineItemVo> orderLineItems) {
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<OrderLineItemVo> getOrderLineItems() {
        return orderLineItems;
    }
}
