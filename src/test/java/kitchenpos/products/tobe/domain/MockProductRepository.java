package kitchenpos.products.tobe.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MockProductRepository implements ProductRepository {

    private final Map<UUID, Product> cache = new HashMap<>();

    @Override
    public Product save(final Product product) {
        cache.put(product.getId(), product);
        return product;
    }

    @Override
    public Optional<Product> findById(final UUID id) {
        return Optional.ofNullable(cache.get(id));
    }
}
