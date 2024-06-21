package kitchenpos.orders.common.application.dto;

import java.util.UUID;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.orders.common.domain.OrderType;
import kitchenpos.orders.common.domain.tobe.MenuQuantity;
import kitchenpos.orders.common.domain.tobe.OrderLineItem;

public class OrderLineItemRequest {

    private UUID menuId;

    private long quantity;

    public OrderLineItemRequest(UUID menuId, long quantity) {
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public OrderLineItem toStore(Menu menu, OrderType orderType) {
        return new OrderLineItem(menu, new MenuQuantity(orderType, quantity));
    }

    public UUID getMenuId() {
        return menuId;
    }
}
