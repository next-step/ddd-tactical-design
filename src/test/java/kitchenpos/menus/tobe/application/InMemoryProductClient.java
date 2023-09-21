package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.menu.TobeProductClient;
import kitchenpos.products.tobe.domain.TobeProductRepository;

import java.math.BigDecimal;
import java.util.UUID;

public class InMemoryProductClient implements TobeProductClient {
    private final TobeProductRepository productRepository;

    public InMemoryProductClient(final TobeProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public BigDecimal getProductPrice(final UUID productId) {
        return productRepository.findById(productId).orElseThrow().getPriceValue();
    }
}
