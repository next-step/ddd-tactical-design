package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.*;
import kitchenpos.products.application.dto.ProductChangePriceRequest;
import kitchenpos.products.application.dto.ProductCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final DisplayedNamePolicy displayedNamePolicy;
    private final ProductEventPublisher productEventPublisher;

    public ProductService(ProductRepository productRepository, DisplayedNamePolicy displayedNamePolicy, ProductEventPublisher productEventPublisher) {

        this.productRepository = productRepository;
        this.displayedNamePolicy = displayedNamePolicy;
        this.productEventPublisher = productEventPublisher;
    }

    @Transactional
    public Product create(final ProductCreateRequest request) {
        Product product = request.toProduct(displayedNamePolicy);
        Product productWithId = product.giveId();
        return productRepository.save(productWithId);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductChangePriceRequest request) {
        final ProductPrice changePrice = ProductPrice.from(request.getPrice());
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(changePrice);
        productEventPublisher.changePrice(new ProductPriceChangeEvent(product.getId()));
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
