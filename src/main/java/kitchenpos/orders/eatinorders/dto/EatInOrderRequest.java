package kitchenpos.orders.eatinorders.dto;

import java.util.List;
import java.util.UUID;

public class EatInOrderRequest {

    private UUID orderTableId;
    private List<EatInOrderLineItemRequest> orderLineItems;

    public EatInOrderRequest(UUID orderTableId, List<EatInOrderLineItemRequest> orderLineItems) {
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }


    public UUID getOrderTableId() {
        return orderTableId;
    }

    public void setOrderTableId(UUID orderTableId) {
        this.orderTableId = orderTableId;
    }

    public List<EatInOrderLineItemRequest> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<EatInOrderLineItemRequest> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }
}
