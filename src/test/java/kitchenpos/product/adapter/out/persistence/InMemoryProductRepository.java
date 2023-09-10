package kitchenpos.product.adapter.out.persistence;

import java.util.*;
import java.util.stream.Collectors;

// TODO: public 제거 필요
public class InMemoryProductRepository implements ProductRepository {
    private final Map<UUID, ProductEntity> products = new HashMap<>();

    @Override
    public ProductEntity save(final ProductEntity product) {
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public Optional<ProductEntity> findById(final UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<ProductEntity> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<ProductEntity> findAllByIdIn(final List<UUID> ids) {
        return products.values()
            .stream()
            .filter(product -> ids.contains(product.getId()))
            .collect(Collectors.toList());
    }
}
