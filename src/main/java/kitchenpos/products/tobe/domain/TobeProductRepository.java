package kitchenpos.products.tobe.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TobeProductRepository {
    TobeProduct save(TobeProduct product);

    Optional<TobeProduct> findById(UUID id);

    List<TobeProduct> findAll();

    List<TobeProduct> findAllByIdIn(List<UUID> ids);
}
