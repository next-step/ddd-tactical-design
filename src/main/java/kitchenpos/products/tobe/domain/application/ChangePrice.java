package kitchenpos.products.tobe.domain.application;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.common.domainevent.DomainEventPublisher;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.domain.vo.ProductPrice;
import kitchenpos.products.tobe.dto.ProductPriceChangeDto;
import org.springframework.stereotype.Component;

@FunctionalInterface
public interface ChangePrice {
    Product execute(final UUID productId, final ProductPriceChangeDto request);
}

@Component
class DefaultChangePrice implements ChangePrice {
    private final ProductRepository productRepository;
    private final DomainEventPublisher domainEventPublisher;

    public DefaultChangePrice(ProductRepository productRepository, DomainEventPublisher domainEventPublisher) {
        this.productRepository = productRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Product execute(UUID productId, ProductPriceChangeDto request) {
        final ProductPrice price = ProductPrice.of(request.getPrice());
        final Product product = productRepository.findById(productId)
                                                 .orElseThrow(NoSuchElementException::new);
        product.changePrice(price, domainEventPublisher);
        productRepository.save(product);
        return product;
    }
}
