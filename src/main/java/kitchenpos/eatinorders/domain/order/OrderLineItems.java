package kitchenpos.eatinorders.domain.order;

import java.util.List;

public class OrderLineItems {
    private final List<OrderLineItem> orderLineItems;

    private OrderLineItems(List<OrderLineItem> orderLineItems) {
        validateNullOrEmpty(orderLineItems);
        this.orderLineItems = orderLineItems;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public int getSize() {
        return orderLineItems.size();
    }

    private void validateNullOrEmpty(List<OrderLineItem> orderLineItems) {
        if (orderLineItems == null || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("주문 품목이 비어 있습니다.");
        }
    }

    public static OrderLineItems of(List<OrderLineItem> orderLineItems) {
        return new OrderLineItems(orderLineItems);
    }

}
