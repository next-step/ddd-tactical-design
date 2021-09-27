package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.menu.Menu;
import kitchenpos.eatinorders.tobe.domain.menu.MenuManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuValidateService {
    private final MenuManager menuManager;

    public MenuValidateService(final MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    void validateOrder(final Order order) {
        final List<OrderLineItem> orderLineItems = order.getOrderLineItems();
        final List<Menu> menus = menuManager.getMenus(orderLineItems.stream()
                .map(OrderLineItem::getMenuId)
                .collect(Collectors.toList()));
        menus.forEach(this::validateMenu);
        if (menus.size() != orderLineItems.size()) {
            throw new IllegalArgumentException("부적절한 메뉴는 주문할 수 없습니다.");
        }
    }

    private void validateMenu(final Menu menu) {
        if (!menu.isDisplayed()) {
            throw new IllegalStateException("숨겨진 메뉴입니다.");
        }
    }
}
