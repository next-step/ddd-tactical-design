package kitchenpos.eatinorders.order.application.dto;

import java.util.UUID;

import kitchenpos.eatinorders.order.domain.OrderLineItem;

public class OrderLineItemResponse {
    private UUID menuId;
    private Long quantity;
    private Long price;

    public OrderLineItemResponse(UUID menuId, Long quantity, Long price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderLineItemResponse(OrderLineItem orderLineItem) {
        this(orderLineItem.getMenuId(), orderLineItem.getQuantity().longValue(), orderLineItem.getPrice().longValue());
    }

    public UUID getMenuId() {
        return menuId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getPrice() {
        return price;
    }
}
