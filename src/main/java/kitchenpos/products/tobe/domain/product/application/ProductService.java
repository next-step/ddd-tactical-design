package kitchenpos.products.tobe.domain.product.application;

import kitchenpos.products.tobe.domain.product.domain.Product;
import kitchenpos.products.tobe.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product create(final Product product) {
        return productRepository.save(product);
    }

    public List<Product> list() {
        return productRepository.findAll();
    }
}
