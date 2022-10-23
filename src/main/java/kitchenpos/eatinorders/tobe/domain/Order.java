package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderType;

import java.util.List;

public class Order {
    private final OrderType orderType;

    private final List<OrderLineItem> orderLineItems;

    public Order(final OrderType orderType, final List<OrderLineItem> orderLineItems) {
        this.orderType = orderType;
        this.orderLineItems = orderLineItems;
    }
}
