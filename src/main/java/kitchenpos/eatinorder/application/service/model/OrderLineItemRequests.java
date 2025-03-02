package kitchenpos.eatinorder.application.service.model;

import java.util.*;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineItemRequests that = (OrderLineItemRequests) o;
        return Objects.equals(orderLineItemRequestsMap, that.orderLineItemRequestsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderLineItemRequestsMap);
    }
}
