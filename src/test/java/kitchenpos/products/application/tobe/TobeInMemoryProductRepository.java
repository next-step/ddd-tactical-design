package kitchenpos.products.application.tobe;

import kitchenpos.products.tobe.domain.TobeProduct;
import kitchenpos.products.tobe.domain.TobeProductRepository;

import java.util.*;
import java.util.stream.Collectors;

public class TobeInMemoryProductRepository implements TobeProductRepository {
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
