package kitchenpos.eatinorders.tobe.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItemRequest {
    private UUID menuId;
    private BigDecimal price;
    private long quantity;

    protected OrderLineItemRequest() {}

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }
}
