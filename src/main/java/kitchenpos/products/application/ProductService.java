package kitchenpos.products.application;

import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.dto.ProductPriceRequest;
import kitchenpos.products.domain.dto.ProductRequest;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductPriceChangeEventPublisher productPriceChangeEventPublisher;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
            final ProductRepository productRepository,
            final ProductPriceChangeEventPublisher productPriceChangeEventPublisher,
            final PurgomalumClient purgomalumClient
    ) {
        this.productRepository = productRepository;
        this.productPriceChangeEventPublisher = productPriceChangeEventPublisher;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Product create(final ProductRequest request) {
        final String name = request.getName();
        final Product product = new Product(UUID.randomUUID(), request.getName(), request.getPrice());

        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }

        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductPriceRequest request) {
        final BigDecimal price = request.getPrice();
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(price);
        productPriceChangeEventPublisher.publishEvent(price, productId);
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
