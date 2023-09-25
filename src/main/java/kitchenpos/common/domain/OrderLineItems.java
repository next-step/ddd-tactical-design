package kitchenpos.common.domain;

import java.util.List;

public class OrderLineItems {
    private final List<OrderLineItem> orderLineItems;

    private OrderLineItems(List<OrderLineItem> orderLineItems) {
        validateNullOrEmpty(orderLineItems);
        validateQuantity(orderLineItems);
        this.orderLineItems = orderLineItems;
    }

    public int getSize() {
        return orderLineItems.size();
    }

    private void validateNullOrEmpty(List<OrderLineItem> orderLineItems) {
        if (orderLineItems == null || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("주문 품목이 비어 있습니다.");
        }
    }

    private static void validateQuantity(final List<OrderLineItem> orderLineItems) {
        // 주문 품목들중에 수량이 0보다 작은게 있는지 확인
        orderLineItems.stream()
                .map(OrderLineItem::getQuantity)
                .filter(quantity -> quantity < 0)
                .findAny()
                .ifPresent(quantity -> {
                    throw new IllegalArgumentException("주문 품목의 수량은 0보다 작을 수 없습니다.");
                });
    }

    public static OrderLineItems of(List<OrderLineItem> orderLineItems) {
        return new OrderLineItems(orderLineItems);
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }
}
