package kitchenpos.products.tobe;

import kitchenpos.apply.products.tobe.domain.Product;
import kitchenpos.apply.products.tobe.domain.ProductRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> products = new HashMap<>();


    @Override
    public Product save(Product product) {
        return products.put(product.getId(), product);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(products.get(id.toString()));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<Product> findAllByIdIn(List<UUID> ids) {
        return products.values()
                .stream()
                .filter(product -> ids.contains(UUID.fromString(product.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public Integer countByIdIn(List<UUID> ids) {
        return Math.toIntExact(products.values()
                .stream()
                .filter(product -> ids.contains(UUID.fromString(product.getId())))
                .count());
    }
}
