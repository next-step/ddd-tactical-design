package kitchenpos.products.application;

import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.products.dto.ProductRequest;
import kitchenpos.products.exception.ProductErrorCode;
import kitchenpos.products.exception.ProductPriceException;
import kitchenpos.products.publisher.ProductPriceChangedEvent;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.common.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProfanityPolicy profanityPolicy;

    private final ApplicationEventPublisher publisher;

    public ProductService(
            final ProductRepository productRepository,
            final ProfanityPolicy profanityPolicy,
            final ApplicationEventPublisher publisher) {
        this.productRepository = productRepository;
        this.profanityPolicy = profanityPolicy;
        this.publisher = publisher;
    }

    @Transactional
    public Product create(final ProductRequest request) {
        Product product = request.toEntity(profanityPolicy);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, BigDecimal price) {
        Product product = findById(productId);
        ProductPrice productPrice = new ProductPrice(price);
        product.changePrice(productPrice);

        try {
            publisher.publishEvent(new ProductPriceChangedEvent(this, productId));
        } catch (RuntimeException ex) {
            throw new ProductPriceException(ProductErrorCode.FAIL_REFLACT_MENU_PRICE);
        }

        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<Product> findAllInById(List<UUID> productIds) {
        return productRepository.findAllByIdIn(productIds);
    }
}
