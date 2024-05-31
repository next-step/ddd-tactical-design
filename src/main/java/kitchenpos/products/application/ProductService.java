package kitchenpos.products.application;

import kitchenpos.products.dto.ProductChangePriceRequest;
import kitchenpos.products.dto.ProductCreateRequest;
import kitchenpos.products.dto.ProductResponse;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.support.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.domain.ProfanityChecker;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProfanityChecker profanityChecker;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final MenuServiceClient menuServiceClient;

    public ProductService(
        final ProductRepository productRepository,
        final ProfanityChecker profanityChecker,
        final ApplicationEventPublisher applicationEventPublisher,
        final MenuServiceClient menuServiceClient
    ) {
        this.productRepository = productRepository;
        this.profanityChecker = profanityChecker;
        this.applicationEventPublisher = applicationEventPublisher;
        this.menuServiceClient = menuServiceClient;
    }

    @Transactional
    public ProductResponse create(final ProductCreateRequest request) {
        final ProductPrice price = ProductPrice.from(request.price());
        final ProductName name = ProductName.from(request.name(), profanityChecker);
        final Product product = productRepository.save(Product.from(name, price));
        return ProductResponse.from(product);
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ProductChangePriceRequest request) {
        final ProductPrice price = ProductPrice.from(request.price());
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(price);
        applicationEventPublisher.publishEvent(ProductChangePriceEvent.from(productId, price));
        menuServiceClient.hideMenuBasedOnProductPrice(productId, price.priceValue());
        return ProductResponse.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        final List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(ProductResponse::from)
                .toList();
    }
}
