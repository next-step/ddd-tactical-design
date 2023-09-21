package kitchenpos.eatinorders.application.orders.dto;

import kitchenpos.eatinorders.domain.orders.EatInOrderLineItemMaterial;

import java.util.List;
import java.util.UUID;

public class EatInOrderCreateRequest {
    private final UUID orderTableId;
    private final List<EatInOrderLineItemMaterial> orderLineItems;

    public EatInOrderCreateRequest(UUID orderTableId, List<EatInOrderLineItemMaterial> orderLineItems) {
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<EatInOrderLineItemMaterial> getOrderLineItems() {
        return orderLineItems;
    }
}
