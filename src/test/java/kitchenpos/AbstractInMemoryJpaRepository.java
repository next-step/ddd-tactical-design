package kitchenpos;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractInMemoryJpaRepository<T, ID> implements JpaRepository<T, ID> {

    private final Map<ID, T> entities = new HashMap<>();
    private final AtomicLong atomicLong = new AtomicLong();
    private Class<T> entityType;

    public AbstractInMemoryJpaRepository() {

        Type superClassType = getClass().getGenericSuperclass();
        if (!(superClassType instanceof ParameterizedType)) {  // sanity check
            throw new IllegalArgumentException("TypeReference는 항상 실제 타입 파라미터 정보와 함께 생성되어야 합니다.");
        }
        entityType = (Class) ((ParameterizedType) superClassType).getActualTypeArguments()[0];
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<T>(entities.values());
    }

    public Optional<T> findById(Long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public <S extends T> S save(S entity) {
        bindId(entity);
        entities.put(getId(entity), entity);
        return entity;
    }

    @Override
    public List<T> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        List<T> list = new ArrayList<>();

        ids.forEach(id -> {
            if (entities.containsKey(id)) {
                list.add(entities.get(id));
            }
        });

        return list;
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public T getOne(ID id) {
        return null;
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return null;
    }


    @Override
    public boolean existsById(ID id) {
        return entities.containsKey(id);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(ID id) {

    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return false;
    }


    private ID getId(T entity) {
        try {
            Field id = entityType.getDeclaredField("id");
            id.setAccessible(true);
            return (ID) id.get(entity);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void bindId(T entity) {
        try {
            Field id = entityType.getDeclaredField("id");
            id.setAccessible(true);

            if (id.get(entity) != null) {
                return;
            }
            id.set(entity, atomicLong.incrementAndGet());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}

