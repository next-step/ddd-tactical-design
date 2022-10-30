package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.entity.Menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuRepository {
    List<Menu> findAllByProductId(UUID productId);

    Menu save(Menu menu);

    Optional<Menu> findById(UUID menuId);

    List<Menu> findAll();
}
