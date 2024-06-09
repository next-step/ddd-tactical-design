package kitchenpos.products.tobe.domain.application;


import kitchenpos.common.domainevent.DomainEventPublisher;
import kitchenpos.products.tobe.domain.repository.ProductRepository;

public class ChangePriceTestFixture extends DefaultChangePrice {

    public ChangePriceTestFixture(ProductRepository productRepository,
                                  DomainEventPublisher domainEventPublisher) {
        super(productRepository, domainEventPublisher);
    }
}
