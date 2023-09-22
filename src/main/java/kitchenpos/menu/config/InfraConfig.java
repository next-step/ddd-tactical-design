package kitchenpos.menu.config;

import kitchenpos.menu.adapter.menu.out.DefaultMenuPurgomalumChecker;
import kitchenpos.menu.adapter.menu.out.DefaultProductFindAdapter;
import kitchenpos.menu.application.menu.port.out.MenuPurgomalumChecker;
import kitchenpos.menu.application.menu.port.out.ProductFindPort;
import kitchenpos.product.application.port.in.ProductFindUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfraConfig {

    @Bean
    public MenuPurgomalumChecker menuPurgomalumChecker() {
        return new DefaultMenuPurgomalumChecker();
    }

    @Bean
    public ProductFindPort productFindPort(final ProductFindUseCase productFindUseCase) {
        return new DefaultProductFindAdapter(productFindUseCase);
    }
}
