package kitchenpos.eatinorders.tobe.infra;

import static kitchenpos.eatinorders.tobe.factory.OrderFactory.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import kitchenpos.eatinorders.tobe.domain.OrderType;
import kitchenpos.eatinorders.tobe.factory.EatInOrderFactory;
import kitchenpos.eatinorders.tobe.factory.OrderFactory;
import kitchenpos.eatinorders.tobe.factory.OrderFactoryProvider;

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
