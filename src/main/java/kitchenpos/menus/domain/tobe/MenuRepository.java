package kitchenpos.menus.domain.tobe;

import kitchenpos.menus.domain.Menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuRepository {
    Menu save(Menu menu);

    Optional<Menu> findById(UUID id);

    Optional<kitchenpos.menus.domain.tobe.Menu> findByUUId(UUID id);

    List<Menu> findAll();

    List<Menu> findAllByIdIn(List<UUID> ids);

    List<Menu> findAllByProductId(UUID productId);
}

