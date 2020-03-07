package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product add(final String name, final Long price) {
        final Product newProduct = new Product(name, price);
        return productRepository.save(newProduct);
    }

    public List<Product> list() {
        return productRepository.findAll();
    }
}
