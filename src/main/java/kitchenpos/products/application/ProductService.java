package kitchenpos.products.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.dto.PriceUpdateDto;
import kitchenpos.products.dto.ProductCreateDto;
import kitchenpos.products.infra.ProductEventPublisher;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final ProductEventPublisher productEventPublisher;

    public ProductService(
        final ProductRepository productRepository,
        final PurgomalumClient purgomalumClient,
        final ProductEventPublisher productEventPublisher
    ) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.productEventPublisher = productEventPublisher;
    }

    @Transactional
    public Product create(final ProductCreateDto productCreateDto) {
        final Product product = productCreateDto.toProduct();
        product.verifySlang(purgomalumClient);

        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final PriceUpdateDto priceUpdateDto) {
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(priceUpdateDto.getPrice());

        productEventPublisher.publishChangePriceEvent(productId);

        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
