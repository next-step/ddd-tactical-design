package kitchenpos.products.application;

import kitchenpos.menus.tobe.domain.menu.ProductClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class InMemoryProductClient implements ProductClient {
    private final ProductRepository productRepository;

    public InMemoryProductClient(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllByIdIn(List<UUID> ids) {
        return productRepository.findAllByIdIn(ids);
    }

    @Override
    public BigDecimal getProductPrice(UUID productId) {
        return productRepository.findById(productId).orElseThrow(IllegalArgumentException::new).getPrice();
    }
}
