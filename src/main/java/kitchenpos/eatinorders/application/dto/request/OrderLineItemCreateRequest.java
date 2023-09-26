package kitchenpos.eatinorders.application.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItemCreateRequest {
    private UUID menuId;
    private long quantity;
    private BigDecimal price;

    public OrderLineItemCreateRequest() {
    }

    public OrderLineItemCreateRequest(UUID menuId, long quantity, BigDecimal price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
