package kitchenpos.eatinorders.application.service;

import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.application.OrderLinePolicy;
import kitchenpos.eatinorders.exception.EatInOrderErrorCode;
import kitchenpos.eatinorders.exception.EatInOrderLineItemException;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuId;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class DefaultOrderLinePolicy implements OrderLinePolicy {
    private final MenuRepository menuRepository;

    public DefaultOrderLinePolicy(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void validate(UUID menuId, Price price) {
        Menu menu = menuRepository.findById(new MenuId(menuId))
                .orElseThrow(NoSuchElementException::new);

        if (!menu.isDisplayed()) {
            throw new EatInOrderLineItemException(EatInOrderErrorCode.MENU_IS_HIDE);
        }

        if (!menu.getPrice().equals(price)) {
            throw new EatInOrderLineItemException(EatInOrderErrorCode.ORDER_PRICE_EQUAL_MENU_PRICE);
        }
    }
}
