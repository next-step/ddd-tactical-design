package kitchenpos.menu.config;

import kitchenpos.menu.application.menu.DefaultMenuFindUseCase;
import kitchenpos.menu.application.menu.DefaultMenuProductPriceUpdateUseCase;
import kitchenpos.menu.application.menu.DefaultMenuRegistrationUseCase;
import kitchenpos.menu.application.menu.port.in.MenuFindUseCase;
import kitchenpos.menu.application.menu.port.in.MenuProductPriceUpdateUseCase;
import kitchenpos.menu.application.menu.port.in.MenuRegistrationUseCase;
import kitchenpos.menu.application.menu.port.out.MenuRepository;
import kitchenpos.menu.application.menu.port.out.ProductFindPort;
import kitchenpos.menu.application.menugroup.port.out.MenuGroupRepository;
import kitchenpos.menu.domain.menu.MenuNameFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public MenuFindUseCase menuFindUseCase(final MenuRepository repository) {
        return new DefaultMenuFindUseCase(repository);
    }

    @Bean
    public MenuProductPriceUpdateUseCase menuProductPriceUpdateUseCase(
        final MenuRepository repository,
        final ProductFindPort productFindPort) {

        return new DefaultMenuProductPriceUpdateUseCase(repository, productFindPort);
    }

    @Bean
    public MenuRegistrationUseCase menuRegistrationUseCase(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final MenuNameFactory menuNameFactory,
        final ProductFindPort productFindPort) {

        return new DefaultMenuRegistrationUseCase(menuRepository, menuGroupRepository,
            menuNameFactory, productFindPort);
    }
}
