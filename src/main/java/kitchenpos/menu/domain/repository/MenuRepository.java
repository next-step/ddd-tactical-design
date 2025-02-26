package kitchenpos.menu.domain.repository;

import java.util.List;
import java.util.Optional;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.product.domain.model.ProductId;

public interface MenuRepository {

    Menu save(Menu menu);

    Optional<Menu> findByMenuId(MenuId id);

    List<Menu> findAll();

    List<Menu> findAllByMenuIdIn(List<MenuId> ids);

    List<Menu> findAllByProductId(ProductId productId);
}

