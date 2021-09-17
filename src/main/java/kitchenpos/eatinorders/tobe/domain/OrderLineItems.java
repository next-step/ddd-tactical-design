package kitchenpos.eatinorders.tobe.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderLineItems {
    private final List<OrderLineItem> orderLineItems;

    public OrderLineItems(final List<OrderLineItem> orderLineItems) {
        this.orderLineItems = Collections.unmodifiableList(orderLineItems);
    }

    public List<OrderLineItem> getOrderLineItems() {
        return new ArrayList<>(orderLineItems);
    }
}
