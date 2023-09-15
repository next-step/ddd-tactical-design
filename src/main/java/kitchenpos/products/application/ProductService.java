package kitchenpos.products.application;

import kitchenpos.products.application.dto.ProductRequest;
import kitchenpos.products.application.dto.ProductResponse;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.PurgomalumClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ProductService(
        final ProductRepository productRepository,
        final PurgomalumClient purgomalumClient,
        final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public ProductResponse create(final ProductRequest request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final String name = request.getName();
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        final Product product = new Product(request.getName(), purgomalumClient, request.getPrice());
        return new ProductResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ProductRequest request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(price);
        applicationEventPublisher.publishEvent(new ProductEvent(productId));
        return new ProductResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }
}
