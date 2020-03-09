package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.dto.ProductRequestDto;
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
    public Product create(final ProductRequestDto productRequestDto) {
        Product product = productRequestDto.toEntity();

        return productRepository.save(product);
    }

    public List<Product> list() {
        return productRepository.findAll();
    }
}
