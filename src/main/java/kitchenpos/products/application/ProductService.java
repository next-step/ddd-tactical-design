package kitchenpos.products.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.products.application.dto.ChangePriceRequest;
import kitchenpos.products.application.dto.CreateProductRequest;
import kitchenpos.products.application.dto.ProductResponse;
import kitchenpos.products.domain.ChangedProductPriceEvent;
import kitchenpos.products.domain.Price;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.PurgomalumClient;

@Service
public class ProductService {
    private final PurgomalumClient purgomalumClient;
    private final ProductRepository productRepository;
    private final ApplicationEventPublisher publisher;

    public ProductService(
            final PurgomalumClient purgomalumClient,
            final ProductRepository productRepository,
            final ApplicationEventPublisher publisher) {
        this.purgomalumClient = purgomalumClient;
        this.productRepository = productRepository;
        this.publisher = publisher;
    }

    @Transactional
    public ProductResponse create(final CreateProductRequest request) {
        final Product product = productRepository.save(
                new Product(
                        UUID.randomUUID(),
                        request.getName(),
                        request.getPrice(),
                        purgomalumClient
                )
        );

        return new ProductResponse(product);
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ChangePriceRequest request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(new Price(request.getPrice()));
        publisher.publishEvent(new ChangedProductPriceEvent(product.getId(), product.getPrice()));
        return new ProductResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }
}
