package kitchenpos.products.tobe.application;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.ui.ProductChangePriceRequest;
import kitchenpos.products.tobe.ui.ProductCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("tobeProductService")
public class ProductService {

    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
            final ProductRepository productRepository,
            final PurgomalumClient purgomalumClient
    ) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Product create(final ProductCreateRequest request) {
        final var id = UUID.randomUUID();
        final var name = new ProductName(request.name(), purgomalumClient);
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
}
