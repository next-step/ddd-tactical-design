package kitchenpos.core.products.application;


import kitchenpos.core.products.tobe.domain.Product;
import kitchenpos.core.products.tobe.domain.TobeProductRepository;
import kitchenpos.core.shared.identifier.ProductId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductRepository implements TobeProductRepository {
    private final Map<ProductId, Product> products = new HashMap<>();

    @Override
    public Product save(final Product product) {
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public Optional<Product> findById(final ProductId id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<Product> findAllByIdIn(final List<ProductId> ids) {
        return products.values()
            .stream()
            .filter(product -> ids.contains(product.getId()))
            .toList();
    }
}
