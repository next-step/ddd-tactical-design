package kitchenpos.global.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryRepository<K, V> {

    private final Map<K, V> elements = new HashMap<>();

    public V save(K key, V value) {
        elements.put(key, value);
        return value;
    }

    public Optional<V> findById(K key) {
        return Optional.ofNullable(elements.get(key));
    }

    public V getById(K id) {
        return findById(id).orElseThrow();
    }

    public List<V> findAll() {
        return elements.values()
            .stream()
            .collect(Collectors.toUnmodifiableList());
    }
}
