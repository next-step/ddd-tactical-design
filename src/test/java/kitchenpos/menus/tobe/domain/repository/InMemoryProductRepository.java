package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<UUID, Product> productMap = new HashMap<>();

    @Override
    public Product save(final Product product) {
        productMap.put(product.getId(), product);
        return product;
    }

    @Override
    public List<Product> findAllByIdIn(final List<UUID> ids) {
        return productMap.values()
                .stream()
                .filter(product -> ids.contains(product.getId()))
                .collect(Collectors.toList());
    }
}
