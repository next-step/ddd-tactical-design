package kitchenpos.product.config;

import kitchenpos.product.adapter.out.DefaultProductPriceChangeEventPublisher;
import kitchenpos.product.adapter.out.DefaultProductPurgomalumChecker;
import kitchenpos.product.application.port.out.ProductPriceChangeEventPublisher;
import kitchenpos.product.application.port.out.ProductPurgomalumChecker;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfraConfig {

    @Bean
    public ProductPurgomalumChecker productPurgomalumChecker() {
        return new DefaultProductPurgomalumChecker();
    }

    @Bean
    public ProductPriceChangeEventPublisher productPriceChangeEventPublisher(
        final ApplicationEventPublisher applicationEventPublisher) {
        
        return new DefaultProductPriceChangeEventPublisher(applicationEventPublisher);
    }
}
