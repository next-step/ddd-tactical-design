package kitchenpos.products.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LegacyProductRepository {
    LegacyProduct save(LegacyProduct product);

    Optional<LegacyProduct> findById(UUID id);

    List<LegacyProduct> findAll();

    List<LegacyProduct> findAllByIdIn(List<UUID> ids);
}

