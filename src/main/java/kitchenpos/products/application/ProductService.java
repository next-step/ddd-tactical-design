package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPurgomalumClient;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.application.request.ProductChangePriceRequest;
import kitchenpos.products.application.request.ProductCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductPurgomalumClient productPurgomalumClient;

    public ProductService(
        final ProductRepository productRepository,
        final ProductPurgomalumClient productPurgomalumClient
    ) {
        this.productRepository = productRepository;
        this.productPurgomalumClient = productPurgomalumClient;
    }

    @Transactional
    public Product create(final ProductCreateRequest request) {
        Product product = new Product(request.getName(), request.getPrice(), productPurgomalumClient);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductChangePriceRequest request) {
        final Product product = productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
        product.changePrice(request.getPrice());
        return productRepository.save(product); // 이벤트 발행
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
