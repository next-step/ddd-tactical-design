package kitchenpos.eatinorders.application.dto;

import kitchenpos.common.domain.OrderLineItem;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItemRequest {
    private final long quantity;

    private final BigDecimal price;

    private final UUID menuId;

    public OrderLineItemRequest(final long quantity, final BigDecimal price, final UUID menuId) {
        this.quantity = quantity;
        this.price = price;
        this.menuId = menuId;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public static OrderLineItem toOrderLineItem(OrderLineItemRequest orderLineItemRequest) {
        return new OrderLineItem(
                orderLineItemRequest.getQuantity(),
                orderLineItemRequest.getMenuId(),
                orderLineItemRequest.getPrice()
        );
    }
}
