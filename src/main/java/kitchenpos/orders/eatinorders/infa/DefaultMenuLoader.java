package kitchenpos.orders.eatinorders.infa;

import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuId;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.orders.eatinorders.application.loader.MenuLoader;
import kitchenpos.orders.eatinorders.domain.OrderedMenu;
import kitchenpos.orders.eatinorders.exception.EatInOrderErrorCode;
import kitchenpos.orders.eatinorders.exception.EatInOrderLineItemException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class DefaultMenuLoader implements MenuLoader {
    private final MenuRepository menuRepository;

    public DefaultMenuLoader(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public OrderedMenu findMenuById(UUID menuId) {
        Menu menu = menuRepository.findById(new MenuId(menuId))
                .orElseThrow(NoSuchElementException::new);

        if (!menu.isDisplayed()) {
            throw new EatInOrderLineItemException(EatInOrderErrorCode.MENU_IS_HIDE);
        }

        return new OrderedMenu(menu.getIdValue(), menu.getNameValue(), menu.getPrice());
    }
}
