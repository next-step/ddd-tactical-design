package kitchenpos.eatinorders.tobe.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kitchenpos.eatinorders.tobe.domain.OrderType;
import kitchenpos.eatinorders.tobe.factory.DefaultOrderFactoryProvider;
import kitchenpos.eatinorders.tobe.factory.EatInOrderFactory;
import kitchenpos.eatinorders.tobe.factory.OrderFactory;
import kitchenpos.eatinorders.tobe.factory.OrderFactoryProvider;

@Configuration
public class OrderFactoryConfiguration {

    @Bean
    public OrderFactoryProvider orderFactoryProvider() {
        final Map<OrderType, OrderFactory> factories = Map.of(
            OrderType.EAT_IN, new EatInOrderFactory()
        );

        return new DefaultOrderFactoryProvider(factories);
    }
}

