package kitchenpos.products.bo;

import kitchenpos.products.dao.ProductDao;
import kitchenpos.products.model.ProductData;

import java.util.*;

public class InMemoryProductDao implements ProductDao {
    private final Map<Long, ProductData> entities = new HashMap<>();

    @Override
    public ProductData save(final ProductData entity) {
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<ProductData> findById(final Long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public List<ProductData> findAll() {
        return new ArrayList<>(entities.values());
    }
}
