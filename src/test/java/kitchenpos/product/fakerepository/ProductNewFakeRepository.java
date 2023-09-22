package kitchenpos.product.fakerepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.product.application.port.out.ProductNewRepository;
import kitchenpos.product.domain.ProductNew;

public class ProductNewFakeRepository implements ProductNewRepository {

    private final Map<UUID, ProductNew> products = new HashMap<>();
    
    @Override
    public ProductNew save(final ProductNew entity) {
        products.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public Optional<ProductNew> findById(final UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<ProductNew> findAll() {
        return products.values()
            .stream()
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<ProductNew> findAllByIdIn(final List<UUID> ids) {
        return ids.stream()
            .filter(id -> products.get(id) != null)
            .map(products::get)
            .collect(Collectors.toUnmodifiableList());
    }
}
