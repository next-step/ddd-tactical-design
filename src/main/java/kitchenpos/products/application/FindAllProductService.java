package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FindAllProductService {
    private final ProductRepository productRepository;

    public FindAllProductService(
        final ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }


    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
