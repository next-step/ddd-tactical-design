package kitchenpos.eatinorders.application.dto;

import kitchenpos.common.domain.OrderLineItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderLineItemResponse {
    private final long quantity;

    private final BigDecimal price;

    private final UUID menuId;

    public OrderLineItemResponse(final long quantity, final BigDecimal price, final UUID menuId) {
        this.quantity = quantity;
        this.price = price;
        this.menuId = menuId;
    }

    public static List<OrderLineItemResponse> ofList(List<OrderLineItem> orderLineItems) {
        return orderLineItems.stream()
                .map(orderLineItem -> new OrderLineItemResponse(orderLineItem.getQuantity(), orderLineItem.getPrice(), orderLineItem.getMenuId()))
                .collect(java.util.stream.Collectors.toList());
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

    public static OrderLineItem toOrderLineItem(OrderLineItemResponse orderLineItemRequest) {
        return new OrderLineItem(
                orderLineItemRequest.getQuantity(),
                orderLineItemRequest.getMenuId(),
                orderLineItemRequest.getPrice()
        );
    }
}
