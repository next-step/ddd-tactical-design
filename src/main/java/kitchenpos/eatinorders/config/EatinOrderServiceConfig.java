package kitchenpos.eatinorders.config;

import kitchenpos.eatinorders.application.eatinorder.DefaultEatinOrderService;
import kitchenpos.eatinorders.application.eatinorder.port.in.EatinOrderUseCase;
import kitchenpos.eatinorders.application.eatinorder.port.out.EatinOrderRepository;
import kitchenpos.eatinorders.application.eatinorder.port.out.ValidMenuPort;
import kitchenpos.eatinorders.application.ordertable.port.out.OrderTableNewRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EatinOrderServiceConfig {

    @Bean
    public EatinOrderUseCase eatinOrderUseCase(final EatinOrderRepository eatinOrderRepository,
        final OrderTableNewRepository orderTableRepository,
        final ValidMenuPort validMenuPort) {

        return new DefaultEatinOrderService(eatinOrderRepository,
            orderTableRepository, validMenuPort);
    }
}
