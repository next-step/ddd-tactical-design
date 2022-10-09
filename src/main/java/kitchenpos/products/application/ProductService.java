package kitchenpos.products.application;

import java.util.List;
import java.util.UUID;
import kitchenpos.event.ProductPriceChangedEvent;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductName;
import kitchenpos.products.domain.ProductPrice;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.exception.ProductNotFoundException;
import kitchenpos.products.ui.request.ProductChangePriceRequest;
import kitchenpos.products.ui.request.ProductCreateRequest;
import kitchenpos.products.ui.response.ProductResponse;
import kitchenpos.profanity.infra.ProfanityCheckClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProfanityCheckClient profanityCheckClient;
    private final ApplicationEventPublisher publisher;

    public ProductService(
        ProductRepository productRepository,
        ProfanityCheckClient profanityCheckClient,
        ApplicationEventPublisher publisher
    ) {
        this.productRepository = productRepository;
        this.profanityCheckClient = profanityCheckClient;
        this.publisher = publisher;
    }

    @Transactional
    public ProductResponse create(final ProductCreateRequest request) {
        ProductName name = new ProductName(request.getName(), profanityCheckClient);
        ProductPrice price = new ProductPrice(request.getPrice());
        Product product = new Product(name, price);

        return ProductResponse.from(productRepository.save(product));
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ProductChangePriceRequest request) {
        ProductPrice newPrice = new ProductPrice(request.getPrice());
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        product.changePrice(newPrice);

        publishChangePriceEvent(product);
        return ProductResponse.from(product);
    }

    private void publishChangePriceEvent(Product product) {
        ProductPriceChangedEvent event = new ProductPriceChangedEvent(
            product.getId(),
            product.getPriceValue()
        );
        publisher.publishEvent(event);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return ProductResponse.of(productRepository.findAll());
    }
}
