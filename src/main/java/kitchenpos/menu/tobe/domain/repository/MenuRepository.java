package kitchenpos.menu.tobe.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menu.tobe.domain.entity.Menu;

public interface MenuRepository {

    Menu save(final Menu menu);

    Optional<Menu> findById(final UUID id);

    List<Menu> findAll();

    List<Menu> findAllByIdIn(final List<UUID> ids);

    List<Menu> findAllByProductId(final UUID productId);
}


