package kitchenpos.orders.eatinorders.ui.dto.request;

import java.util.List;
import java.util.UUID;

public class EatInOrderRestRequest {

    final private UUID orderTableId;
    final private List<EatInOrderLineItemRestRequest> orderLineItems;

    public EatInOrderRestRequest(UUID orderTableId, List<EatInOrderLineItemRestRequest> orderLineItems) {
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<EatInOrderLineItemRestRequest> getOrderLineItems() {
        return orderLineItems;
    }

}
