package kitchenpos.products.tobe.fixtures;

import kitchenpos.products.tobe.Product;
import kitchenpos.products.tobe.ProductRepository;

import java.util.*;

public class FakeProductRepository implements ProductRepository {

    private Map<UUID, Product> inMemory = new HashMap<>();

    @Override
    public Product save(Product product) {
        inMemory.put(product.id(), product);
        return inMemory.get(product.id());
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(inMemory.get(id));
    }

    @Override
    public List<Product> findAll() {
        return inMemory.values().stream().toList();
    }

    @Override
    public List<Product> findAllByIdIn(List<UUID> ids) {
        return inMemory.values().stream()
                .filter(it -> ids.contains(it.id()))
                .toList();
    }
}
