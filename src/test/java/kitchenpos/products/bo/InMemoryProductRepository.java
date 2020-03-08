package kitchenpos.products.bo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import kitchenpos.AbstractInMemoryJpaRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;

public class InMemoryProductRepository extends
    AbstractInMemoryJpaRepository<Product, Long> implements ProductRepository {

    private final Map<Long, Product> entities = new HashMap<>();
    private AtomicLong atomicLong = new AtomicLong();

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public <S extends Product> S save(S entity) {
        bindId(entity);
        entities.put(entity.getId(), entity);
        return entity;
    }

    private void bindId(Product entity) {
        try {
            Field id = Product.class.getDeclaredField("id");
            id.setAccessible(true);
            id.set(entity, atomicLong.incrementAndGet());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
