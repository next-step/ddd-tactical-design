package kitchenpos.order.infra;

import static kitchenpos.order.factory.OrderFactory.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import kitchenpos.order.domain.OrderType;
import kitchenpos.order.factory.DeliveryOrderFactory;
import kitchenpos.order.factory.EatInOrderFactory;
import kitchenpos.order.factory.OrderFactory;
import kitchenpos.order.factory.OrderFactoryProvider;
import kitchenpos.order.factory.TakeoutOrderFactory;

public class InMemoryOrderFactoryProvider implements OrderFactoryProvider {
    private final Map<OrderType, OrderFactory> factories = new HashMap<>();

    public InMemoryOrderFactoryProvider() {
        factories.put(OrderType.DELIVERY, new DeliveryOrderFactory());
        factories.put(OrderType.EAT_IN, new EatInOrderFactory());
        factories.put(OrderType.TAKEOUT, new TakeoutOrderFactory());
    }

    @Override
    public OrderFactory getFactory(OrderType type) {
        return Optional.ofNullable(factories.get(type))
            .orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_TYPE_ERROR));
    }
}
