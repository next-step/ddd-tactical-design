package kitchenpos.eatinorders.tobe.domain;

import java.util.List;

public class OrderLineItems {
    private final List<OrderLineItem> orderLineItems;

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }
}
