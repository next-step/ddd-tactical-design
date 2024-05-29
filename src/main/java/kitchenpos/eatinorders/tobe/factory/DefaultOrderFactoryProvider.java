package kitchenpos.eatinorders.tobe.factory;

import static kitchenpos.order.factory.OrderFactory.*;

import java.util.Map;
import java.util.Optional;

import kitchenpos.eatinorders.tobe.domain.OrderType;

public class DefaultOrderFactoryProvider implements OrderFactoryProvider {
    private final Map<OrderType, OrderFactory> factories;

    public DefaultOrderFactoryProvider(Map<OrderType, OrderFactory> factories) {
        this.factories = factories;
    }

    public OrderFactory getFactory(OrderType type) {
        return Optional.ofNullable(factories.get(type))
            .orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_TYPE_ERROR));
    }
}
