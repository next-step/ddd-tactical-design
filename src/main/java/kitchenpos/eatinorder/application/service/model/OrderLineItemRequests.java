package kitchenpos.eatinorder.application.service.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderLineItemRequests {
    private final Map<UUID, OrderLineItemRequest> orderLineItemRequestsMap = new HashMap<>();

    public OrderLineItemRequests(final List<OrderLineItemRequest> orderLineItemRequests) {
        if (orderLineItemRequests == null || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException("주문 항목은 하나 이상 포함되어야 합니다.");
        }
        orderLineItemRequests.forEach(orderLineItemRequest -> orderLineItemRequestsMap.put(orderLineItemRequest.getMenuId(), orderLineItemRequest));
    }

    public long getQuantity(UUID menuId) {
        return orderLineItemRequestsMap.get(menuId).getQuantity();
    }

    public long getPrice(UUID menuId) {
        return orderLineItemRequestsMap.get(menuId).getPrice().longValue();
    }

    public List<UUID> getMenuIds() {
        return orderLineItemRequestsMap.keySet()
                .stream()
                .toList();
    }

    public int size() {
        return orderLineItemRequestsMap.size();
    }
}
