package kitchenpos.product.infra.persistence;

import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.repository.ProductRepository;

import java.util.*;

public class FakeProductRepository implements ProductRepository {

    private final Map<UUID, Product> storage;

    public FakeProductRepository(Map<UUID, Product> storage) {
        this.storage = storage;
    }

    @Override
    public List<Product> findAllByIdIn(List<UUID> ids) {
        return new ArrayList<>(storage.values().stream()
                .filter(p -> ids.contains(p.getId()))
                .toList());
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Product save(Product product) {
        UUID id = UUID.randomUUID();
        product.setId(id);
        storage.put(id, product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(storage.values());
    }
}
