package kitchenpos.eatinorders.tobe.eatinorder.ui.dto;

import kitchenpos.eatinorders.tobe.eatinorder.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.eatinorder.domain.Quantity;
import kitchenpos.menus.tobe.menu.domain.MenuId;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItemCreateRequest {
    private final UUID menuId;
    private final long quantity;
    private final BigDecimal price;

    public OrderLineItemCreateRequest(final UUID menuId, final long quantity, final BigDecimal price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderLineItem toEntity() {
        return new OrderLineItem(new MenuId(menuId), new Quantity(quantity));
    }
}
