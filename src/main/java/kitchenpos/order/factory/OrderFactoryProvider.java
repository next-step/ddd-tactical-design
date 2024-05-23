package kitchenpos.order.factory;

import kitchenpos.order.domain.OrderType;

public interface OrderFactoryProvider {
    OrderFactory getFactory(OrderType type);
}
