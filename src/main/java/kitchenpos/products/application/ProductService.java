package kitchenpos.products.application;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.products.dto.ProductRequest;
import kitchenpos.products.dto.ProductResponse;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final kitchenpos.products.tobe.domain.ProductRepository productRepository;
    private final ProfanityPolicy profanityPolicy;
    private final ApplicationEventPublisher publisher;

    public ProductService(
            final kitchenpos.products.tobe.domain.ProductRepository productRepository,
            final ProfanityPolicy profanityPolicy,
            final ApplicationEventPublisher publisher) {
        this.productRepository = productRepository;
        this.profanityPolicy = profanityPolicy;
        this.publisher = publisher;
    }

    @Transactional
    public ProductResponse create(final ProductRequest request) {
        Product response = productRepository.save(request.toEntity(profanityPolicy));
        return ProductResponse.fromEntity(response);
    }

    @Transactional
    public ProductResponse changePrice(final ProductId productId, BigDecimal price) {
        Product product = findById(productId);
        Price productPrice = new Price(price);

        product.changePrice(productPrice);
        productRepository.save(product);

        return ProductResponse.fromEntity(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<Product> responses = productRepository.findAll();
        return ProductResponse.fromEntities(responses);
    }


    private Product findById(ProductId productId) {
        return productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAllInById(List<ProductId> productIds) {
        List<Product> responses = productRepository.findAllByIdIn(productIds);
        return ProductResponse.fromEntities(responses);
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(ProductId productId) {
        Product product = findById(productId);
        return ProductResponse.fromEntity(product);
    }
}
