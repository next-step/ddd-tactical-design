package kitchenpos.products.domain.tobe.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToBeProductRepository {
    ToBeProduct save(ToBeProduct toBeProduct);

    Optional<ToBeProduct> findById(UUID id);

    List<ToBeProduct> findAll();

    List<ToBeProduct> findAllByIdIn(List<UUID> ids);
}

