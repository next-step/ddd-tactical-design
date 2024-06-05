package kitchenpos.menus.domain.tobe.menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menus.application.dto.MenuRequest;

public interface MenuRepository {
    Menu save(Menu menu);

    Optional<Menu> findById(UUID id);

    List<Menu> findAll();

    List<Menu> findAllByIdIn(List<UUID> ids);

    List<Menu> findAllByProductId(UUID productId);
}

