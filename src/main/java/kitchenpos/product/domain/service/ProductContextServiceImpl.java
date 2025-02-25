package kitchenpos.product.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;
import kitchenpos.menu.domain.service.ProductContextService;
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

    @Transactional(readOnly = true)
    @Override
    public BigDecimal getTotalPrice(UUID productId, BigDecimal qty) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_PRODUCT.toString()));

        return product.getPrice()
            .price()
            .multiply(qty);
    }
}
