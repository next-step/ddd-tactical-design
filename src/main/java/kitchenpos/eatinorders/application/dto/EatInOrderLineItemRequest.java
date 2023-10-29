package kitchenpos.eatinorders.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class EatInOrderLineItemRequest {
    private long quantity;
    private UUID menuId;
    private BigDecimal price;

    public EatInOrderLineItemRequest(long quantity, UUID menuId, BigDecimal price) {
        this.quantity = quantity;
        this.menuId = menuId;
        this.price = price;
    }

    public static EatInOrderLineItemRequest create(long quantity, UUID menuId, BigDecimal price) {
        return new EatInOrderLineItemRequest(quantity, menuId, price);
    }

    public long getQuantity() {
        return quantity;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
