package kitchenpos.menus.application;

import kitchenpos.menus.application.ProductQueryService;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryProductQueryService implements ProductQueryService {
    private final Map<UUID, Product> products = new HashMap<>();

    public Product save(final Product product) {
        products.put(product.getId(), product);
        return product;
    }


    public Optional<Product> findById(final UUID id) {
        return Optional.ofNullable(products.get(id));
    }


    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Map<UUID, BigDecimal> findAllByIdIn(final List<UUID> ids) {
        return products.values()
            .stream()
            .filter(product -> ids.contains(product.getId()))
            .collect(Collectors.toMap(Product::getId, Product::getPrice));
    }
}
