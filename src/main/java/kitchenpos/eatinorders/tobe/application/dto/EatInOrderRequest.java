package kitchenpos.eatinorders.tobe.application.dto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class EatInOrderRequest {

    private List<OrderLineItemRequest> orderLineItems;

    private UUID orderTableId;

    public EatInOrderRequest(List<OrderLineItemRequest> orderLineItems, UUID orderTableId) {
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    public List<OrderLineItemRequest> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<OrderLineItemRequest> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public void setOrderTableId(UUID orderTableId) {
        this.orderTableId = orderTableId;
    }

    public void validate() {
        validateOrderLineItems();
        validateOrderTableId();
    }

    private void validateOrderLineItems() {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private void validateOrderTableId() {
        if (Objects.isNull(orderTableId)) {
            throw new IllegalArgumentException();
        }
    }
}
