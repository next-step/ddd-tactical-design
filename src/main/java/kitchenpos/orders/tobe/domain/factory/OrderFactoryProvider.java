package kitchenpos.orders.tobe.domain.factory;

import kitchenpos.orders.tobe.domain.OrderType;

public interface OrderFactoryProvider {
    OrderFactory getFactory(OrderType type);
}
