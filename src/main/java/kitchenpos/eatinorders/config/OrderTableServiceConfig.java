package kitchenpos.eatinorders.config;

import kitchenpos.eatinorders.application.ordertable.DefaultOrderTableCreationService;
import kitchenpos.eatinorders.application.ordertable.DefaultOrderTableSitService;
import kitchenpos.eatinorders.application.ordertable.port.in.OrderTableCreationUseCase;
import kitchenpos.eatinorders.application.ordertable.port.in.OrderTableSitUseCase;
import kitchenpos.eatinorders.application.ordertable.port.out.OrderTableNewRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderTableServiceConfig {

    @Bean
    public OrderTableCreationUseCase orderTableCreationUseCase(
        final OrderTableNewRepository repository) {

        return new DefaultOrderTableCreationService(repository);
    }

    @Bean
    public OrderTableSitUseCase orderTableSitUseCase(final OrderTableNewRepository repository) {

        return new DefaultOrderTableSitService(repository);
    }
}
