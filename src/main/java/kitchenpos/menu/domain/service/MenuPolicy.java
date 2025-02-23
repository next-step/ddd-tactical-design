package kitchenpos.menu.domain.service;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuVo;

public interface MenuPolicy {
    void hideMenu(UUID productId);
    MenuVo.MenuInfo changePrice(UUID menuId, MenuPrice price);
    MenuVo.MenuInfo display(UUID menuId);

    void validateMenuPrice(MenuPrice price, List<MenuProduct> menuProducts);
}
