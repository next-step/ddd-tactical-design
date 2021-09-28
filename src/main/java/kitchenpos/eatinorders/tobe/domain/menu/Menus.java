package kitchenpos.eatinorders.tobe.domain.menu;

import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.exception.IllegalMenuException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Menus {
    private final List<Menu> menus;

    private Menus(final List<Menu> menus) {
        menus.forEach(this::validateMenu);
        this.menus = Collections.unmodifiableList(menus);
    }

    public static Menus from(final MenuManager menuManager, final Order order) {
        final List<OrderLineItem> orderLineItems = order.getOrderLineItems();
        final List<Menu> menus = menuManager.getMenus(orderLineItems.stream()
                .map(OrderLineItem::getMenuId)
                .collect(Collectors.toList()));
        if (menus.size() != orderLineItems.size()) {
            throw new IllegalMenuException("부적절한 메뉴는 주문할 수 없습니다.");
        }
        return new Menus(menus);
    }

    public List<Menu> getMenus() {
        return new ArrayList<>(menus);
    }

    private void validateMenu(final Menu menu) {
        if (!menu.isDisplayed()) {
            throw new IllegalMenuException("숨겨진 메뉴입니다.");
        }
    }
}
