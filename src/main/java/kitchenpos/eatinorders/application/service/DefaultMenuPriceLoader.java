package kitchenpos.eatinorders.application.service;

import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.application.MenuPriceLoader;
import kitchenpos.eatinorders.exception.EatInOrderErrorCode;
import kitchenpos.eatinorders.exception.EatInOrderLineItemException;
import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultMenuPriceLoader implements MenuPriceLoader {
    private MenuService menuService;

    public DefaultMenuPriceLoader(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public Price findMenuPriceById(UUID menuId) {
        Menu menu = menuService.findById(new MenuId(menuId));
        if(!menu.isDisplayed()){
            throw new EatInOrderLineItemException(EatInOrderErrorCode.MENU_IS_HIDE);
        }
        return menu.getPrice();
    }
}
