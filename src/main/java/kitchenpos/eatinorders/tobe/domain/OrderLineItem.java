package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;

public class OrderLineItem {
    private final Menu menu;
    private final long quantity;

    public OrderLineItem(final Menu menu, final long quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }
}
