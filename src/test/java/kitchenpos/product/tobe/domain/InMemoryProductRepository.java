package kitchenpos.product.tobe.domain;

import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductRepository;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<UUID, kitchenpos.product.domain.Product> products = new HashMap<>();

    @Override
    public kitchenpos.product.domain.Product save(final kitchenpos.product.domain.Product product) {
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public Optional<kitchenpos.product.domain.Product> findById(final UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<kitchenpos.product.domain.Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<Product> findAllByIdIn(final List<UUID> ids) {
        return products.values()
            .stream()
            .filter(product -> ids.contains(product.getId()))
            .toList();
    }
}
