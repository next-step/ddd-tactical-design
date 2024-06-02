package kitchenpos.menus.domain.tobe.menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menus.domain.Menu;

public interface MenuRepository {
    kitchenpos.menus.domain.Menu save(kitchenpos.menus.domain.Menu menu);

    Optional<kitchenpos.menus.domain.Menu> findById(UUID id);

    List<kitchenpos.menus.domain.Menu> findAll();

    List<kitchenpos.menus.domain.Menu> findAllByIdIn(List<UUID> ids);

    List<Menu> findAllByProductId(UUID productId);
}

