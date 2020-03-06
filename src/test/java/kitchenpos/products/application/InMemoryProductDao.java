package kitchenpos.products.application;

import kitchenpos.products.infrastructure.dao.ProductDao;
import kitchenpos.products.tobe.domain.Product;

import java.util.*;

public class InMemoryProductDao implements ProductDao {
    private final Map<Long, Product> entities = new HashMap<>();

    @Override
    public Product save(final Product entity) {
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Product> findById(final Long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(entities.values());
    }
}
