package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductValidator {

    private final ProductRepository productRepository;

    public ProductValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public void isExistProductIn(List<UUID> productIds) {

        Integer productSize = productRepository.countByIdIn(productIds);

        if (productSize != productIds.size()) {
            throw new IllegalArgumentException();
        }
    }
}
