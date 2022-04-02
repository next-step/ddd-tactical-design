package kitchenpos.menus.tobe.domain.menu;

import java.util.List;
import kitchenpos.products.tobe.domain.ProductId;

public interface MenuRepository {
    Menu save(Menu menu);

    Menu findById(MenuId id);

    List<Menu> findAll();

    List<Menu> findAllByIdIn(List<MenuId> ids);

    List<Menu> findAllByProductId(ProductId productId);
}

