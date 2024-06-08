package kitchenpos.menus.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menus.domain.tobe.menu.Menu;

public interface MenuRepository {

    Menu save(Menu menu);

    Optional<Menu> findById(UUID id);

    List<Menu> findAll();

    List<Menu> findAllByIdIn(List<UUID> ids);

    List<Menu> findAllByProductId(UUID productId);
}

