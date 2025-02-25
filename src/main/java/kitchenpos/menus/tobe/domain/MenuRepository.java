package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.vo.MenuId;

import java.util.List;
import java.util.Optional;

public interface MenuRepository {
    Menu save(Menu menu);

    Optional<Menu> findById(MenuId id);

    List<Menu> findAll();

    List<Menu> findAllByIdIn(List<MenuId> ids);

    List<Menu> findAllByProductId(Long productId);
}
