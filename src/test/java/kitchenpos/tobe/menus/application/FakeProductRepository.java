package kitchenpos.tobe.menus.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.tobe.domain.model.Product;
import kitchenpos.menus.tobe.domain.repository.ProductRepository;

public class FakeProductRepository implements ProductRepository {

    public static final UUID INVALID_ID = new UUID(0L, 0L);

    @Override
    public List<Product> findAllByIdIn(List<UUID> productIds) {
        return productIds.stream()
                .filter(uuid -> !uuid.equals(INVALID_ID))
                .map(uuid -> new Product(uuid, "치킨", BigDecimal.valueOf(19_000L)))
                .collect(Collectors.toList());
    }
}
