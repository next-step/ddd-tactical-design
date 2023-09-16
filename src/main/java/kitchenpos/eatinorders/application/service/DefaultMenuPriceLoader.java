package kitchenpos.eatinorders.application.service;

import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.application.MenuPriceLoader;
import kitchenpos.eatinorders.exception.EatInOrderErrorCode;
import kitchenpos.eatinorders.exception.EatInOrderLineItemException;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuId;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class DefaultMenuPriceLoader implements MenuPriceLoader {
    private final MenuRepository menuRepository;

    public DefaultMenuPriceLoader(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Price findMenuPriceById(UUID menuId) {
        Menu menu = menuRepository.findById(new MenuId(menuId))
                .orElseThrow(NoSuchElementException::new);

        if (!menu.isDisplayed()) {
            throw new EatInOrderLineItemException(EatInOrderErrorCode.MENU_IS_HIDE);
        }

        return menu.getPrice();
    }
}
