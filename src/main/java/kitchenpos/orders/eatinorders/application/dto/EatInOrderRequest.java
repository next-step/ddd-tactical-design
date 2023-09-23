package kitchenpos.orders.eatinorders.application.dto;

import java.util.List;
import java.util.UUID;

public class EatInOrderRequest {

    final private UUID orderTableId;
    final private List<EatInOrderLineItemRequest> orderLineItems;

    public EatInOrderRequest(UUID orderTableId, List<EatInOrderLineItemRequest> orderLineItems) {
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<EatInOrderLineItemRequest> getOrderLineItems() {
        return orderLineItems;
    }

}
