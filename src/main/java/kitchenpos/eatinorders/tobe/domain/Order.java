package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderType;

public class Order {
    private OrderType orderType;

    public Order(final OrderType orderType) {
        this.orderType = orderType;
    }
}
