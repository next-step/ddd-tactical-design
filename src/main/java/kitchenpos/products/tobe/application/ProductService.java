package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.*;
import kitchenpos.products.tobe.ui.ProductChangePriceRequest;
import kitchenpos.products.tobe.ui.ProductCreateRequest;
import kitchenpos.products.tobe.ui.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("tobeProductService")
public class ProductService {

    private final ProductRepository productRepository;
    private final ProfanityChecker profanityChecker;

    public ProductService(
            final ProductRepository productRepository,
            final ProfanityChecker profanityChecker
    ) {
        this.productRepository = productRepository;
        this.profanityChecker = profanityChecker;
    }

    @Transactional
    public Product create(final ProductCreateRequest request) {
        final var id = UUID.randomUUID();
        final var name = new ProductName(request.name(), profanityChecker);
        final var price = new ProductPrice(request.price());
        return productRepository.save(new Product(id, name, price));
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductChangePriceRequest price) {
        final var product = productRepository.findById(productId)
                .orElseThrow(IllegalArgumentException::new);
        return product.changePrice(new ProductPrice(price.price()));
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> listByIds(final List<UUID> productIds) {
        List<Product> products = productRepository.findAllByIdIn(productIds);
        return products.stream()
                .map(product -> new ProductResponse(product.getId(), product.getPrice().value()))
                .toList();
    }
}
