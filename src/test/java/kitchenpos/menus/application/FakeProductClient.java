package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.ProductClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;

import java.math.BigDecimal;
import java.util.UUID;

public class FakeProductClient implements ProductClient {
    private final ProductRepository productRepository;

    public FakeProductClient(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public BigDecimal productPrice(UUID productId) {
        return productRepository.findById(productId).map(Product::getPrice)
                .orElseThrow();
    }
}
