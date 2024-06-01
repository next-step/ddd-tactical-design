package kitchenpos.product.tobe.application;

import kitchenpos.common.infra.PurgomalumClient;
import kitchenpos.product.tobe.application.dto.ChangePriceRequest;
import kitchenpos.product.tobe.application.dto.CreateProductRequest;
import kitchenpos.common.event.publisher.ProductPriceChangedEvent;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductRepository;
import kitchenpos.product.tobe.domain.validate.ProfanityValidator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service("newProductService")
public class ProductService {
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final ApplicationEventPublisher eventPublisher;

    public ProductService(
            final ProductRepository productRepository,
            final PurgomalumClient purgomalumClient,
            final ApplicationEventPublisher eventPublisher
    ) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.eventPublisher = eventPublisher;

    }

    @Transactional
    public Product create(final CreateProductRequest request) {
        final Product product = new Product(UUID.randomUUID(), request.name(), request.price(), new ProfanityValidator(purgomalumClient));
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ChangePriceRequest request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(request.price());

        eventPublisher.publishEvent(new ProductPriceChangedEvent(productId, request.price()));

        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
