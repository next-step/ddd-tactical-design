package kitchenpos.products.application;

import kitchenpos.products.application.dto.ProductRequest;
import kitchenpos.products.application.dto.ProductResponse;
import kitchenpos.products.domain.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductPurgomalumClient purgomalumClient;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ProductService(
        final ProductRepository productRepository,
        final ProductPurgomalumClient purgomalumClient,
        final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public ProductResponse create(final ProductRequest request) {
        final ProductName name = ProductName.of(request.getName(), purgomalumClient);
        final ProductPrice price = ProductPrice.of(request.getPrice());
        final Product product = new Product(name, price);

        return new ProductResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ProductRequest request) {
        final Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));
        final ProductPrice price = ProductPrice.of(request.getPrice());

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
