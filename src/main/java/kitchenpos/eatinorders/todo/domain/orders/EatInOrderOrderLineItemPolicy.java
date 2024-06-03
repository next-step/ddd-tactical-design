package kitchenpos.eatinorders.todo.domain.orders;

import kitchenpos.eatinorders.exception.KitchenPosIllegalArgumentException;
import kitchenpos.eatinorders.exception.KitchenPosIllegalStateException;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.support.domain.MenuClient;
import kitchenpos.support.domain.OrderLineItem;

import java.math.BigDecimal;

import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.MENU_IS_HIDE;
import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.MENU_PRICE_IS_NOT_SAME;

public class EatInOrderOrderLineItemPolicy {
    private final MenuClient menuClient;

    public EatInOrderOrderLineItemPolicy(MenuClient menuClient) {
        this.menuClient = menuClient;
    }

    public void checkMenu(OrderLineItem orderLineItem) {
        Menu menu = menuClient.getMenu(orderLineItem.getMenuId());
        checkHideMenu(menu);
        checkDisMatchMenuPrice(orderLineItem.getPrice(), menu);
    }

    private static void checkDisMatchMenuPrice(BigDecimal menuPriceRequest, Menu menu) {
        if (menu.getPrice().compareTo(menuPriceRequest) != 0) {
            throw new KitchenPosIllegalArgumentException(
                    MENU_PRICE_IS_NOT_SAME, menu.getId(), menu.getPrice(), menuPriceRequest);
        }
    }

    private static void checkHideMenu(Menu menu) {
        if (!menu.isDisplayed()) {
            throw new KitchenPosIllegalStateException(MENU_IS_HIDE, menu.getId());
        }
    }
}
