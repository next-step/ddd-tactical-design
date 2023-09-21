package kitchenpos.product.config;

import kitchenpos.product.application.DefaultProductFindUseCase;
import kitchenpos.product.application.DefaultProductPriceChangeUseCase;
import kitchenpos.product.application.DefaultProductRegistrationUseCase;
import kitchenpos.product.application.port.in.ProductFindUseCase;
import kitchenpos.product.application.port.in.ProductPriceChangeUseCase;
import kitchenpos.product.application.port.in.ProductRegistrationUseCase;
import kitchenpos.product.application.port.out.ProductNewRepository;
import kitchenpos.product.application.port.out.ProductPriceChangeEventPublisher;
import kitchenpos.product.domain.ProductNameFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ProductFindUseCase productFindUseCase(final ProductNewRepository repository) {
        return new DefaultProductFindUseCase(repository);
    }

    @Bean
    public ProductPriceChangeUseCase productPriceChangeUseCase(
        final ProductNewRepository repository,
        final ProductPriceChangeEventPublisher eventPublisher) {

        return new DefaultProductPriceChangeUseCase(repository, eventPublisher);
    }

    @Bean
    public ProductRegistrationUseCase productRegistrationUseCase(
        final ProductNameFactory productNameFactory,
        final ProductNewRepository repository) {

        return new DefaultProductRegistrationUseCase(productNameFactory, repository);
    }
}
