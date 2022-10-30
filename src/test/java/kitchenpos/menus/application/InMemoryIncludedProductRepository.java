package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.entity.IncludedProduct;
import kitchenpos.menus.tobe.domain.repository.IncludedProductRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryIncludedProductRepository implements IncludedProductRepository {
    private final Map<UUID, IncludedProduct> includedProducts = new HashMap<>();

    @Override
    public Optional<IncludedProduct> findById(UUID id) {
        return Optional.ofNullable(includedProducts.get(id));
    }

    public IncludedProduct save(IncludedProduct includedProduct) {
        includedProducts.put(includedProduct.getId(), includedProduct);
        return includedProduct;
    }
}
