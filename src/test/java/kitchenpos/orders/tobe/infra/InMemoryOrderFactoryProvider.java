package kitchenpos.orders.tobe.infra;

import static kitchenpos.order.factory.OrderFactory.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import kitchenpos.orders.tobe.domain.OrderType;
import kitchenpos.orders.tobe.domain.factory.EatInOrderFactory;
import kitchenpos.orders.tobe.domain.factory.OrderFactory;
import kitchenpos.orders.tobe.domain.factory.OrderFactoryProvider;

public class InMemoryOrderFactoryProvider implements OrderFactoryProvider {
    private final Map<OrderType, OrderFactory> factories = new HashMap<>();

    public InMemoryOrderFactoryProvider() {
        factories.put(OrderType.EAT_IN, new EatInOrderFactory());
    }

    @Override
    public OrderFactory getFactory(OrderType type) {
        return Optional.ofNullable(factories.get(type))
            .orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_TYPE_ERROR));
    }
}
