package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.ProductClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class ProductClientImpl implements ProductClient {
    private final ProductRepository productRepository;

    public ProductClientImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public BigDecimal productPrice(UUID productId) {
        return productRepository.findById(productId).map(Product::getPrice)
                .orElseThrow();
    }

    @Override
    public int countMatchingProductIdIn(List<UUID> productIds) {
        return productRepository.findAllByIdIn(productIds).size();
    }
}
