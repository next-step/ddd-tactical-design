package kitchenpos.eatinorders.tobe.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItemRequest {
    private UUID menuId;
    private BigDecimal price;
    private Long quantity;

    protected OrderLineItemRequest() {
    }

    public OrderLineItemRequest(final UUID menuId, final BigDecimal price, final Long quantity) {
        this.menuId = menuId;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }
}
