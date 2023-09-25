package kitchenpos.eatinorders.application.dto;

import java.util.List;
import java.util.UUID;

public class EatInOrderRequest {
    private List<EatInOrderLineItemRequest> eatInOrderLineItemRequests;
    private UUID orderTableId;

    public EatInOrderRequest(UUID orderTableId, List<EatInOrderLineItemRequest> eatInOrderLineItemRequests) {
        this.orderTableId = orderTableId;
        this.eatInOrderLineItemRequests = eatInOrderLineItemRequests;
    }

    public static EatInOrderRequest create(UUID orderTableId, List<EatInOrderLineItemRequest> eatInOrderLineItemRequests) {
        return new EatInOrderRequest(orderTableId, eatInOrderLineItemRequests);
    }

    public List<EatInOrderLineItemRequest> getOrderLineItems() {
        return eatInOrderLineItemRequests;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
