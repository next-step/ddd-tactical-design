package kitchenpos.menus.tobe.intrastructure;

import kitchenpos.menus.tobe.domain.menu.ProductClient;
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
    public BigDecimal getProductPrice(final UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product price를 조회할 수 없습니다. Product Id가 존재하지 않습니다. productId: " + productId))
                .getPriceValue();
    }

    @Override
    public void validProductIds(List<UUID> productIds) {
        final List<Product> products = productRepository.findAllByIdIn(productIds);
        if (products.size() != productIds.size()) {
            throw new IllegalArgumentException();
        }
    }
}
