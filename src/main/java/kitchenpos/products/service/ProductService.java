package kitchenpos.products.service;

import kitchenpos.products.dto.ProductRequest;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product create(final ProductRequest request) {
        return productRepository.save(
                Product.from(request.getName(), request.getPrice())
        );
    }

    public List<Product> list() {
        return productRepository.findAll();
    }
}
