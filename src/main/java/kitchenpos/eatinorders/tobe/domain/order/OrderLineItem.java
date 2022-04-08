package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.common.domain.Money;
import kitchenpos.menus.tobe.domain.menu.MenuId;

public final class OrderLineItem {

    private final OrderLineItemId id;
    private final MenuId menuId;
    private final Money menuPrice;
    private final long quantity;

    public OrderLineItem(OrderLineItemId id, MenuId menuId, Money menuPrice, long quantity) {
        this.id = id;
        this.menuId = menuId;
        this.menuPrice = menuPrice;
        this.quantity = quantity;
    }

    public OrderLineItem(MenuId menuId, Money menuPrice, long quantity) {
        this(null, menuId, menuPrice, quantity);
    }
}
