package kitchenpos.products.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    ProductRecord save(ProductRecord product);

    Optional<ProductRecord> findById(UUID id);

    List<ProductRecord> findAll();

    List<ProductRecord> findAllByIdIn(List<UUID> ids);
}
