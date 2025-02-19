package kitchenpos.product.application.service;

import kitchenpos.shared.domain.Profanities;
import kitchenpos.product.application.exception.ProductNotFoundException;
import kitchenpos.product.application.port.in.ChangeProductPriceUseCase;
import kitchenpos.product.application.port.in.CreateProductUseCase;
import kitchenpos.product.application.port.in.LoadProductListUseCase;
import kitchenpos.product.application.port.out.LoadProductPort;
import kitchenpos.product.application.port.out.SaveProductPort;
import kitchenpos.product.application.service.model.ChangeProductPriceRequest;
import kitchenpos.product.application.service.model.CreateProductRequest;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.model.ProductName;
import kitchenpos.product.domain.model.ProductPrice;
import kitchenpos.shared.event.DomainEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService implements CreateProductUseCase, ChangeProductPriceUseCase, LoadProductListUseCase {
    private final LoadProductPort loadProductPort;
    private final SaveProductPort saveProductPort;
    private final Profanities profanities;
    private final ApplicationEventPublisher eventPublisher;

    public ProductService(
            final LoadProductPort loadProductPort,
            final SaveProductPort saveProductPort,
            final Profanities profanities,
            final ApplicationEventPublisher eventPublisher
    ) {
        this.loadProductPort = loadProductPort;
        this.saveProductPort = saveProductPort;
        this.profanities = profanities;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public Product create(final CreateProductRequest request) {
        ProductName productName = ProductName.of(request.getName(), profanities);
        ProductPrice productPrice = ProductPrice.of(request.getPrice());
        final Product product = new Product(UUID.randomUUID(), productName, productPrice);
        return saveProductPort.save(product);
    }

    @Override
    @Transactional
    public Product changePrice(final UUID productId, final ChangeProductPriceRequest request) {
        final Product product = loadProductPort.findById(productId)
            .orElseThrow(ProductNotFoundException::new);
        product.changePrice(request.getPrice());
        Product savedProduct = saveProductPort.save(product);
        publishEvent(product);
        return savedProduct;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return loadProductPort.findAll();
    }

    private void publishEvent(Product product) {
        List<DomainEvent> domainEvents = product.getDomainEvents();
        domainEvents.forEach(eventPublisher::publishEvent);
        product.clearDomainEvents();
    }
}
