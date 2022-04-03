package kitchenpos.products.domain.tobe.domain;

import kitchenpos.products.domain.tobe.domain.vo.ProductId;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTobeProductRepository implements TobeProductRepository {
    private final Map<ProductId, TobeProduct> products = new HashMap<>();

    @Override
    public TobeProduct save(TobeProduct product) {
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public Optional<TobeProduct> findById(ProductId id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<TobeProduct> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<TobeProduct> findAllByIdIn(List<ProductId> ids) {
        return products.values()
                .stream()
                .filter(product -> ids.contains(product.getId()))
                .collect(Collectors.toList());
    }
}
