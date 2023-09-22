package kitchenpos.products.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.products.domain.ChangedProductPriceEvent;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ApplicationEventPublisher publisher;

    public ProductService(
            final ProductRepository productRepository,
            final ApplicationEventPublisher publisher) {
        this.productRepository = productRepository;
        this.publisher = publisher;
    }

    @Transactional
    public Product create(final CreateProductRequest request) {
        return productRepository.save(new Product(UUID.randomUUID(), request.getDisplayedName(), request.getPrice()));
    }

    @Transactional
    public Product changePrice(final UUID productId, final ChangePriceRequest request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(request.getPrice());
        publisher.publishEvent(new ChangedProductPriceEvent(product.getId(), product.getPrice()));
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
