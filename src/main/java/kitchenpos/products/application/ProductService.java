package kitchenpos.products.application;


import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.Products;
import kitchenpos.products.event.ProductPriceChangeEvent;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final ApplicationEventPublisher publisher;

    public ProductService(
            final ProductRepository productRepository,
            final PurgomalumClient purgomalumClient,
            final ApplicationEventPublisher publisher) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.publisher = publisher;
    }

    @Transactional
    public Product create(final Product request) {
        return productRepository.save(new Product(request, purgomalumClient));

    }

    @Transactional
    public Product changePrice(final UUID productId, final Product request) {
        Product product = productRepository.save(getProduct(productId))
                .changePrice(request.getPrice());
        publisher.publishEvent(new ProductPriceChangeEvent(productId, request.getPrice()));
        return product;
    }

    private Product getProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public Products findAllByIdIn(List<UUID> productIds) {
        return new Products(productRepository.findAllByIdIn(productIds));
    }

    @Transactional(readOnly = true)
    public Product findById(UUID productId) {
        return getProduct(productId);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }


}
