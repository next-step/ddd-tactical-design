package kitchenpos.products.application;

import java.util.List;
import java.util.UUID;
import kitchenpos.products.domain.ProductPrice;
import kitchenpos.products.domain.ProductProfanityCheckClient;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.exception.ProductNotFoundException;
import kitchenpos.products.ui.request.ProductChangePriceRequest;
import kitchenpos.products.ui.request.ProductCreateRequest;
import kitchenpos.products.ui.response.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductProfanityCheckClient profanityCheckClient;

    public ProductService(
        ProductRepository productRepository,
        ProductProfanityCheckClient profanityCheckClient
    ) {
        this.productRepository = productRepository;
        this.profanityCheckClient = profanityCheckClient;
    }

    @Transactional
    public ProductResponse create(final ProductCreateRequest request) {
        Product product = new Product(
            request.getName(),
            request.getPrice(),
            profanityCheckClient
        );

        return ProductResponse.from(productRepository.save(product));
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ProductChangePriceRequest request) {
        ProductPrice newPrice = new ProductPrice(request.getPrice());
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        product.changePrice(newPrice);

        return ProductResponse.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return ProductResponse.of(productRepository.findAll());
    }
}
