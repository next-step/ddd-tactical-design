package kitchenpos.menu.tobe.infra;

import kitchenpos.menu.tobe.domain.menu.ProductApiClient;
import kitchenpos.product.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProductApiClientImpl implements ProductApiClient {
    private final ProductRepository productRepository;

    public ProductApiClientImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public long countProductsByIds(List<UUID> productIds) {
        return productRepository.findAllByIdIn(productIds).size();
    }
}
