package kitchenpos.domain;

import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;

import java.util.*;

public class FakeProductRepository implements ProductRepository {

    private final HashMap<UUID, Product> inMemory = new HashMap<>();

    @Override
    public List<Product> findAllByIdIn(List<UUID> ids) {
        return ids.stream()
                .map(inMemory::get)
                .toList();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(inMemory.get(id));
    }

    @Override
    public Product save(Product product) {
        inMemory.put(product.getId(), product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(inMemory.values());
    }
}
