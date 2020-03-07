package kitchenpos.products.service;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.*;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<Long, Product> entities = new HashMap<>();
    volatile private Long id = 1L;

    @Override
    public Product save(final Product entity) {
        setId(entity);
        entities.put(id, entity);
        id++;
        return entity;
    }

    private void setId(Product entity) {
        try {
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Product> findById(final Long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public List<Product> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Product> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() { return 0; }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Product entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Product> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Product> List<S> saveAll(Iterable<S> entities) {
        ArrayList<S> saved = new ArrayList<>();
        for (S entity : entities) {
            Product product = save(entity);
            saved.add((S) product);
        }
        return saved;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Product> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Product> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Product getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends Product> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Product> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Product> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Product> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Product> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Product> boolean exists(Example<S> example) {
        return false;
    }
}
