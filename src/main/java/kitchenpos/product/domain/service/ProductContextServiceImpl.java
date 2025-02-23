package kitchenpos.product.domain.service;

import java.util.List;
import java.util.UUID;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductContextServiceImpl implements ProductContextService {

    private final ProductRepository productRepository;

    public ProductContextServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAllByIds(List<UUID> productIds) {
        return productRepository.findAllByIdIn(productIds);
    }
}
