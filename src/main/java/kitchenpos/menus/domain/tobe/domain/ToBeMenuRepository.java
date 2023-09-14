package kitchenpos.menus.domain.tobe.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToBeMenuRepository {
    ToBeMenu save(ToBeMenu menu);

    Optional<ToBeMenu> findById(UUID id);

    List<ToBeMenu> findAll();

    List<ToBeMenu> findAllByIdIn(List<UUID> ids);

    List<ToBeMenu> findAllByProductId(UUID productId);
}
