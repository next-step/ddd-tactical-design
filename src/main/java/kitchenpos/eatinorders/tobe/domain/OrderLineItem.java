package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;

public class OrderLineItem {
    private static final String INVALID_MENU = "숨겨진 메뉴는 주문할 수 없습니다.";

    private final Menu menu;
    private final int quantity;

    public OrderLineItem(Menu menu, int quantity) {
        validate(menu);
        this.menu = menu;
        this.quantity = quantity;
    }

    private void validate(Menu menu) {
        if (!menu.isDisplayed()) {
            throw new IllegalStateException(INVALID_MENU);
        }
    }
}
