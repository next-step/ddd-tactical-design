package kitchenpos.menus.tobe.infrastructure;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.vo.MenuId;

import java.util.List;

public interface MenuDao {
    void save(Menu menu);

    Menu findById(MenuId id, List<MenuProduct> menuProducts);

    Menu findAllById(MenuId id, List<MenuProduct> menuProducts);

    List<Menu> findAllByIds(List<MenuId> ids, List<MenuProduct> menuProducts);
}
