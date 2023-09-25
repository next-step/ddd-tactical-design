package kitchenpos.eatinorders.application.dto;

import kitchenpos.common.domain.OrderLineItems;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderRequest {

    private final UUID orderTableId;

    private final List<OrderLineItemRequest> orderLineItems;

    public OrderRequest(UUID orderTableId, List<OrderLineItemRequest> orderLineItems) {
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public OrderRequest(List<OrderLineItemRequest> orderLineItems) {
        this.orderTableId = null;
        this.orderLineItems = orderLineItems;
    }

    public OrderLineItems getOrderLineItems() {
        return OrderLineItems.of(orderLineItems.stream()
                .map(OrderLineItemRequest::toOrderLineItem)
                .collect(Collectors.toList()));
    }
    public UUID getOrderTableId() {
        return orderTableId;
    }
}
