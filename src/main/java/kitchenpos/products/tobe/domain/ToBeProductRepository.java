package kitchenpos.products.tobe.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToBeProductRepository {
    ToBeProduct save(ToBeProduct product);

    Optional<ToBeProduct> findById(UUID id);

    List<ToBeProduct> findAll();

    List<ToBeProduct> findAllByIdIn(List<UUID> ids);
}

