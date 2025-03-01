package kitchenpos.menu.domain.service;

import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuProducts;
import kitchenpos.menu.domain.model.MenuVo;
import kitchenpos.product.domain.model.ProductId;

public interface MenuPolicy {
    void hideMenu(ProductId productId);
    MenuVo.MenuInfo changePrice(MenuId menuId, MenuPrice price);
    MenuVo.MenuInfo display(MenuId menuId);

    void validateMenuPrice(MenuPrice price, MenuProducts menuProducts);
}
