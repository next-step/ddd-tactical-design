package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.TobeProduct;
import kitchenpos.products.tobe.domain.TobeProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements TobeProductRepository {
    private final Map<UUID, TobeProduct> products = new HashMap<>();

    @Override
    public TobeProduct save(final TobeProduct product) {
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public Optional<TobeProduct> findById(final UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<TobeProduct> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<TobeProduct> findAllByIdIn(final List<UUID> ids) {
        return products.values()
            .stream()
            .filter(product -> ids.contains(product.getId()))
            .collect(Collectors.toList());
    }
}
