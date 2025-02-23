package kitchenpos.menus.infrastructure;

import kitchenpos.menus.tobe.domain.MenuProduct;

import java.util.List;
import java.util.UUID;

public interface MenuProductDao {
    void saveAll(List<MenuProduct> menuProducts);

    List<MenuProduct> findAllByMenuId(UUID menuId);

    List<MenuProduct> findAll();

    List<MenuProduct> findAllByMenuIds(List<UUID> ids);

    List<MenuProduct> findAllBySeq(long seq);
}
