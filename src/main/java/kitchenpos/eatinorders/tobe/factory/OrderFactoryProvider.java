package kitchenpos.eatinorders.tobe.factory;

import kitchenpos.eatinorders.tobe.domain.OrderType;

public interface OrderFactoryProvider {
    OrderFactory getFactory(OrderType type);
}
