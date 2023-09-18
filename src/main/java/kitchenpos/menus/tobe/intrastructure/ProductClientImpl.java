package kitchenpos.menus.tobe.intrastructure;

import kitchenpos.menus.tobe.domain.menu.ProductClient;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."))
                .getPriceValue();
    }
}
