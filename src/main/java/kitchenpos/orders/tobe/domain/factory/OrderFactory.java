package kitchenpos.orders.tobe.domain.factory;

import kitchenpos.orders.tobe.domain.Order;
import kitchenpos.orders.tobe.domain.OrderLineItems;
import kitchenpos.orders.tobe.domain.OrderTable;
import kitchenpos.orders.tobe.domain.OrderType;

public interface OrderFactory {
    Order createOrder(OrderType orderType, OrderLineItems orderLineItems, OrderTable orderTable, String deliveryAddress);
}
