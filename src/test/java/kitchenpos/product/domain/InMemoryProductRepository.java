package kitchenpos.product.domain;

import kitchenpos.product.adapter.out.persistence.ProductEntity;
import kitchenpos.product.adapter.out.persistence.ProductRepository;

import java.util.*;
import java.util.stream.Collectors;

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
