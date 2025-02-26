package kitchenpos.menus.tobe.infrastructure;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.vo.MenuId;

import java.util.List;

public interface MenuProductDao {
    void saveAll(List<MenuProduct> menuProducts);

    List<MenuProduct> findAllByMenuId(MenuId menuId);

    List<MenuProduct> findAll();

    List<MenuProduct> findAllByMenuIds(List<MenuId> ids);

    List<MenuProduct> findAllBySeq(long seq);
}
