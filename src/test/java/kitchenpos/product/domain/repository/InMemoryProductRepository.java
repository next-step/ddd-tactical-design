package kitchenpos.product.domain.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.model.ProductId;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<ProductId, Product> products = new HashMap<>();

    @Override
    public List<Product> findAllByProductIdIn(List<ProductId> ids) {
        return products.values().stream()
            .filter(product -> ids.contains(product.getProductId()))
            .toList();
    }

    @Override
    public Product save(Product product) {
        final var id = ProductId.of(UUID.randomUUID());
        product.setProductId(id);
        products.put(id, product);
        return product;
    }

    @Override
    public Optional<Product> findByProductId(ProductId productId) {
        return Optional.ofNullable(products.get(productId));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
}
