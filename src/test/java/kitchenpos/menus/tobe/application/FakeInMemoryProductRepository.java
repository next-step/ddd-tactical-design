package kitchenpos.menus.tobe.application;

import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;

import java.util.*;
import java.util.stream.Collectors;

public class FakeInMemoryProductRepository implements ProductRepository {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    private final Map<UUID, Product> products = new HashMap<>();

    @Override
    public Product save(final Product product) {
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public Optional<Product> findById(final UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<Product> findAllByIdIn(final List<UUID> ids) {
        return ids.stream()
                .filter(uuid -> !uuid.equals(INVALID_ID))
                .map(uuid -> new Product())
                .collect(Collectors.toList());
    }
}
