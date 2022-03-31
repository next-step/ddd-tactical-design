package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.*;
import kitchenpos.products.tobe.ui.dto.ProductChangePriceRequest;
import kitchenpos.products.tobe.ui.dto.ProductCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final Profanities profanities;
    private final ProductProducer eventPublisher;

    @Autowired
    public ProductService(final ProductRepository productRepository, final ProductProducer eventPublisher) {
        this(productRepository, new DefaultProfanities(), eventPublisher);
    }

    public ProductService(final ProductRepository productRepository, final Profanities profanities, final ProductProducer eventPublisher) {
        this.productRepository = productRepository;
        this.profanities = profanities;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Product create(final ProductCreateRequest request) {
        final Product product = request.toEntity(profanities);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(UUID productId, ProductChangePriceRequest request) {
        final Product product = findById(productId);
        product.changePrice(request.price());
        eventPublisher.publish(productId);
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findById(final UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<Product> findAllByIdIn(List<UUID> productIds) {
        return productRepository.findAllByIdIn(productIds);
    }
}
