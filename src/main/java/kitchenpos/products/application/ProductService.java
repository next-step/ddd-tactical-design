package kitchenpos.products.application;

import kitchenpos.products.application.dto.ProductRequest;
import kitchenpos.products.application.dto.ProductResponse;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.ProductPurgomalumClient;
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
    private final ProductPurgomalumClient productPurgomalumClient;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ProductService(
        final ProductRepository productRepository,
        final ProductPurgomalumClient purgomalumClient,
        final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.productRepository = productRepository;
        this.productPurgomalumClient = purgomalumClient;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public ProductResponse create(final ProductRequest request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격이 0원 미만입니다.");
        }
        final String name = request.getName();
        if (Objects.isNull(name) || productPurgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("상품명이 없거나 비속어가 포함되어 있습니다.");
        }
        final Product product = new Product(request.getName(), productPurgomalumClient, request.getPrice());
        return new ProductResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ProductRequest request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격이 0원 미만입니다.");
        }
        final Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다. [" + productId.toString()+ "]"));
        product.changePrice(price);
        applicationEventPublisher.publishEvent(new ProductChangePriceEvent(productId));
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
