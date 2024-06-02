package kitchenpos.orders.tobe.application;

import static kitchenpos.order.factory.OrderFactory.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import kitchenpos.orders.tobe.domain.factory.DeliveryOrderFactory;
import kitchenpos.orders.tobe.domain.factory.EatInOrderFactory;
import kitchenpos.orders.tobe.domain.factory.TakeoutOrderFactory;
import kitchenpos.orders.tobe.domain.OrderType;
import kitchenpos.orders.tobe.domain.factory.OrderFactory;
import kitchenpos.orders.tobe.domain.factory.OrderFactoryProvider;

@Component
public class DefaultOrderFactoryProvider implements OrderFactoryProvider {
    private final Map<OrderType, OrderFactory> factories = new EnumMap<>(OrderType.class);

    public DefaultOrderFactoryProvider() {
        this.factories.put(OrderType.DELIVERY, new DeliveryOrderFactory());
        this.factories.put(OrderType.EAT_IN, new EatInOrderFactory());
        this.factories.put(OrderType.TAKEOUT, new TakeoutOrderFactory());
    }

    public OrderFactory getFactory(OrderType type) {
        return Optional.ofNullable(factories.get(type))
            .orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_TYPE_ERROR));
    }
}
