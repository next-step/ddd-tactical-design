package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductId;
import kitchenpos.products.tobe.domain.ProductRepository;

import java.util.*;

public class ImMemoryProductRepository implements ProductRepository {
    private final Map<Long, Product> entities = new HashMap<>();

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public void save(Product product) {
        entities.put(product.getId(), product);
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return Optional.ofNullable(entities.get(id.getValue()));
    }
}
