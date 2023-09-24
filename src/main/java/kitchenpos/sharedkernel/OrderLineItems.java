package kitchenpos.sharedkernel;

import kitchenpos.menus.tobe.domain.Menu;

public abstract class OrderLineItems {

    protected void validateMenu(final Menu menu) {
        if (menu == null) {
            throw new IllegalArgumentException();
        }
    }

    protected void validateMenuPrice(final OrderLineItem orderLineItem, final Menu menu) {
        if (menu.getBigDecimalPrice().compareTo(orderLineItem.getPrice()) != 0) {
            throw new IllegalArgumentException();
        }
    }
}
