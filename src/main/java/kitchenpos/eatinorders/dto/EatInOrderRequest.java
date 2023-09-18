package kitchenpos.eatinorders.dto;

import kitchenpos.eatinorders.application.MenuLoader;
import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.domain.EatInOrderLineItems;
import kitchenpos.eatinorders.domain.OrderTableId;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EatInOrderRequest {

    private UUID orderTableId;
    private List<EatInOrderLineItemRequest> orderLineItems;

    public EatInOrderRequest(UUID orderTableId, List<EatInOrderLineItemRequest> orderLineItems) {
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public EatInOrder toEntity(MenuLoader policy) {
        return new EatInOrder(
                new EatInOrderLineItems(
                        orderLineItems.stream()
                        .map(item -> item.toEntity(policy))
                        .collect(Collectors.toUnmodifiableList())),
                new OrderTableId(orderTableId)
        );
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
