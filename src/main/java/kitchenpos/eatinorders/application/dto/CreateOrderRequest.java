package kitchenpos.eatinorders.application.dto;

import java.util.List;
import java.util.UUID;

public class CreateOrderRequest {
    private UUID orderTableId;
    private List<OrderLineItemDto> orderLineItems;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(UUID orderTableId, List<OrderLineItemDto> orderLineItems) {
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<OrderLineItemDto> getOrderLineItems() {
        return orderLineItems;
    }
}
