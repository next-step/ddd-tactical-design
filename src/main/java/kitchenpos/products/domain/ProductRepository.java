package kitchenpos.products.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    ProductAsis save(ProductAsis product);

    Optional<ProductAsis> findById(UUID id);

    List<ProductAsis> findAll();

    List<ProductAsis> findAllByIdIn(List<UUID> ids);
}

