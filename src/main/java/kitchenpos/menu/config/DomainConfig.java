package kitchenpos.menu.config;

import kitchenpos.menu.application.menu.port.out.MenuPurgomalumChecker;
import kitchenpos.menu.domain.menu.DefaultMenuNameFactory;
import kitchenpos.menu.domain.menu.MenuNameFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public MenuNameFactory menuNameFactory(final MenuPurgomalumChecker checker) {
        return new DefaultMenuNameFactory(checker);
    }
}
