package kitchenpos.products.tobe.domain;

import java.util.*;

public class InMemoryToBeProductRepository implements ToBeProductRepository {

    private final Map<UUID, ToBeProduct> products = new HashMap<>();

    @Override
    public ToBeProduct save(final ToBeProduct product) {
        products.put(product.getProductId(), product);
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
                .filter(product -> ids.contains(product.getProductId()))
                .toList();
    }
}
