package kitchenpos.product.config;

import kitchenpos.product.application.port.out.ProductPurgomalumChecker;
import kitchenpos.product.domain.DefaultProductNameFactory;
import kitchenpos.product.domain.ProductNameFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public ProductNameFactory productNameFactory(final ProductPurgomalumChecker checker) {
        return new DefaultProductNameFactory(checker);
    }
}
