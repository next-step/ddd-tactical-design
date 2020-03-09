package kitchenpos.products.tobe.domain.usecase;

import java.util.List;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.entity.ProductRepository;
import kitchenpos.products.tobe.web.dto.ProductSaveRequestDto;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(final ProductSaveRequestDto requestDto) {
        return productRepository.save(requestDto.toEntity());
    }

    public List<Product> list() {
        return productRepository.findAll();
    }
}
