package kitchenpos.order.tobe.eatinorder.application.dto;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderLineItem;

public class CreateEatInOrderRequest {

    private UUID orderTableId;
    private List<EatInOrderLineItem> orderLineItems;

    public CreateEatInOrderRequest(UUID orderTableId, List<EatInOrderLineItem> orderLineItems) {
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<EatInOrderLineItem> getOrderLineItems() {
        return Collections.unmodifiableList(orderLineItems);
    }
}
