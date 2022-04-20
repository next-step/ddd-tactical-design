package kitchenpos.eatinorders.tobe.dto;

import kitchenpos.common.domain.Quantity;
import kitchenpos.eatinorders.tobe.domain.EatInOrderLineItem;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EatInOrderCreationRequest {
    private final UUID orderTableId;
    private final Map<UUID, Quantity> eatInOrderLineItems;

    public EatInOrderCreationRequest(final UUID orderTableId, final Map<UUID, Quantity> eatInOrderLineItems) {
        this.orderTableId = orderTableId;
        this.eatInOrderLineItems = eatInOrderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public Map<UUID, Quantity> getEatInOrderLineItems() {
        return eatInOrderLineItems;
    }
}
