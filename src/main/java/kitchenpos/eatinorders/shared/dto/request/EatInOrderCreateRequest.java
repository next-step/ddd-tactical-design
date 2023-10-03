package kitchenpos.eatinorders.shared.dto.request;

import kitchenpos.eatinorders.shared.dto.EatInOrderLineItemDto;

import java.util.List;
import java.util.UUID;

public class EatInOrderCreateRequest {
    private List<EatInOrderLineItemDto> orderLineItems;
    private UUID orderTableId;

    public EatInOrderCreateRequest(List<EatInOrderLineItemDto> orderLineItems, UUID orderTableId) {
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    public List<EatInOrderLineItemDto> getOrderLineItems() {
        return orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
