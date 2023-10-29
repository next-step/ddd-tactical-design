package kitchenpos.menus.application;

import kitchenpos.menus.domain.NewProduct;
import kitchenpos.menus.domain.ProductRepostiory;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements ProductRepostiory {
    private final Map<UUID, NewProduct> products = new HashMap<>();

    public NewProduct save(final NewProduct product) {
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public List<NewProduct> findAllByIdIn(final List<UUID> ids) {
        return products.values()
                .stream()
                .filter(product -> ids.contains(product.getId()))
                .collect(Collectors.toList());
    }
}
