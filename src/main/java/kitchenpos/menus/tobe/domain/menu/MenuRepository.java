package kitchenpos.menus.tobe.domain.menu;

import java.util.List;
import java.util.Optional;
import kitchenpos.common.domain.MenuId;

public interface MenuRepository {

    Menu save(Menu menu);

    Optional<Menu> findById(MenuId id);

    List<Menu> findAll();

    List<Menu> findAllByIdIn(List<MenuId> ids);

    // List<Menu> findAllByProductId(ProductId productId);
}
