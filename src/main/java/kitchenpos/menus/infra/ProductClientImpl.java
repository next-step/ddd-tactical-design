package kitchenpos.menus.infra;

import kitchenpos.menus.tobe.domain.menu.ProductClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductClientImpl implements ProductClient {
    private final ProductRepository productRepository;

    public ProductClientImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllByIdIn(List<UUID> productIds) {
        return productRepository.findAllByIdIn(productIds);
    }

    @Override
    public BigDecimal getProductPrice(UUID productId) {
        return productRepository.findById(productId).orElseThrow(IllegalArgumentException::new).getPrice();
    }
}
