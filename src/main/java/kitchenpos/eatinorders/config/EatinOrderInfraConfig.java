package kitchenpos.eatinorders.config;

import kitchenpos.eatinorders.adapter.eatinorder.out.ValidMenuAdapter;
import kitchenpos.eatinorders.application.eatinorder.port.out.ValidMenuPort;
import kitchenpos.menu.application.menu.port.in.MenuFindUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EatinOrderInfraConfig {

    @Bean
    public ValidMenuPort validMenuPort(final MenuFindUseCase menuFindUseCase) {
        return new ValidMenuAdapter(menuFindUseCase);
    }
}
