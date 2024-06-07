package kitchenpos.menu.tobe.infra;

import kitchenpos.menu.tobe.domain.menu.ProductClient;
import kitchenpos.product.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProductClientImpl implements ProductClient {
    private final ProductRepository productRepository;

    public ProductClientImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public long countProductsByIds(List<UUID> productIds) {
        return productRepository.findAllByIdIn(productIds).size();
    }
}
