package kitchenpos.products.application;

import kitchenpos.products.domain.ProductRecord;
import kitchenpos.products.domain.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<UUID, ProductRecord> products = new HashMap<>();

    @Override
    public ProductRecord save(final ProductRecord product) {
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public Optional<ProductRecord> findById(final UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<ProductRecord> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<ProductRecord> findAllByIdIn(final List<UUID> ids) {
        return products.values()
            .stream()
            .filter(product -> ids.contains(product.getId()))
            .toList();
    }
}
