package kitchenpos.product.tobe.application;

import kitchenpos.common.event.publisher.ProductPriceChangedEvent;
import kitchenpos.common.infra.PurgomalumClient;
import kitchenpos.product.tobe.application.dto.request.ChangePriceRequest;
import kitchenpos.product.tobe.application.dto.request.CreateProductRequest;
import kitchenpos.product.tobe.application.dto.response.ProductResponse;
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
    public ProductResponse create(final CreateProductRequest request) {
        final Product product = new Product(UUID.randomUUID(), request.name(), request.price(), new ProfanityValidator(purgomalumClient));
        return toProductResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ChangePriceRequest request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(request.price());

        eventPublisher.publishEvent(new ProductPriceChangedEvent(productId, request.price()));

        return toProductResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(this::toProductResponse)
                .toList();
    }

    private ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }
}
