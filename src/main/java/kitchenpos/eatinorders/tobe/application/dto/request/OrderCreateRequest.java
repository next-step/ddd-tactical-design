package kitchenpos.eatinorders.tobe.application.dto.request;

import kitchenpos.eatinorders.tobe.domain.OrderType;

import java.util.List;
import java.util.UUID;

public class OrderCreateRequest {
    private OrderType type;
    private UUID orderTableId;
    private List<OrderLineItemCreateRequest> orderLineItemCreateRequests;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(OrderType type, UUID orderTableId, List<OrderLineItemCreateRequest> orderLineItemCreateRequests) {
        this.type = type;
        this.orderTableId = orderTableId;
        this.orderLineItemCreateRequests = orderLineItemCreateRequests;
    }

    public OrderType getType() {
        return type;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<OrderLineItemCreateRequest> getOrderLineItemCreateRequests() {
        return orderLineItemCreateRequests;
    }
}
