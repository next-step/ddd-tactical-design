package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.ToBeProduct;
import kitchenpos.products.tobe.domain.ToBeProductRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements ToBeProductRepository {
    private final Map<UUID, ToBeProduct> products = new HashMap<>();

    @Override
    public ToBeProduct save(final ToBeProduct product) {
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public Optional<ToBeProduct> findById(final UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<ToBeProduct> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<ToBeProduct> findAllByIdIn(final List<UUID> ids) {
        return products.values()
            .stream()
            .filter(product -> ids.contains(product.getId()))
            .collect(Collectors.toList());
    }
}
