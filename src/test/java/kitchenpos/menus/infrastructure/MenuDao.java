package kitchenpos.menus.infrastructure;

import kitchenpos.menus.tobe.Menu;
import kitchenpos.menus.tobe.MenuProduct;

import java.util.List;
import java.util.UUID;

public interface MenuDao {
    void save(Menu menu);

    Menu findById(UUID id, List<MenuProduct> menuProducts);

    Menu findAllById(UUID id, List<MenuProduct> menuProducts);

    List<Menu> findAllByIds(List<UUID> ids, List<MenuProduct> menuProducts);
}
