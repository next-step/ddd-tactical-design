package kitchenpos.order.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kitchenpos.order.domain.OrderType;
import kitchenpos.order.factory.DefaultOrderFactoryProvider;
import kitchenpos.order.factory.DeliveryOrderFactory;
import kitchenpos.order.factory.EatInOrderFactory;
import kitchenpos.order.factory.OrderFactory;
import kitchenpos.order.factory.OrderFactoryProvider;
import kitchenpos.order.factory.TakeoutOrderFactory;

@Configuration
public class OrderFactoryConfiguration {

    @Bean
    public OrderFactoryProvider orderFactoryProvider() {
        final Map<OrderType, OrderFactory> factories = Map.of(
            OrderType.DELIVERY, new DeliveryOrderFactory(),
            OrderType.EAT_IN, new EatInOrderFactory(),
            OrderType.TAKEOUT, new TakeoutOrderFactory()
        );

        return new DefaultOrderFactoryProvider(factories);
    }
}

