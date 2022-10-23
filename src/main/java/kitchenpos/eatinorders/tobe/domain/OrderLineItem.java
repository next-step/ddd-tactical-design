package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;

public class OrderLineItem {
    private final Menu menu;
    public OrderLineItem(final Menu menu) {
        this.menu = menu;
    }
}
