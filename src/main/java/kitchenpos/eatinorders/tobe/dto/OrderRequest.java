package kitchenpos.eatinorders.tobe.dto;

import java.util.List;
import java.util.UUID;

public class OrderRequest {
    private String type;
    private UUID orderTableId;
    private List<OrderLineItemRequest> orderLineItems;

    protected OrderRequest() {
    }

    public String getType() {
        return type;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<OrderLineItemRequest> getOrderLineItems() {
        return orderLineItems;
    }
}
