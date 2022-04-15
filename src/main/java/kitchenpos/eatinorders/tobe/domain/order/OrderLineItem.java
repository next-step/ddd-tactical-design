package kitchenpos.eatinorders.tobe.domain.order;

import java.util.Objects;
import kitchenpos.common.domain.Money;
import kitchenpos.menus.tobe.domain.menu.MenuId;

public final class OrderLineItem {

    private final OrderLineItemId id;
    private final MenuId menuId;
    private final Money menuPrice;
    private final long quantity;

    public OrderLineItem(OrderLineItemId id, MenuId menuId, Money menuPrice, long quantity) {
        if (Objects.isNull(menuId)) {
            throw new IllegalArgumentException("menuId는 null일 수 없습니다.");
        }
        this.id = id;
        this.menuId = menuId;
        this.menuPrice = menuPrice;
        this.quantity = quantity;
    }

    public OrderLineItem(MenuId menuId, Money menuPrice, long quantity) {
        this(null, menuId, menuPrice, quantity);
    }
}
