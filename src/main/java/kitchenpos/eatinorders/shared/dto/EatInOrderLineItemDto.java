package kitchenpos.eatinorders.shared.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class EatInOrderLineItemDto {
    private UUID menuId;
    private long quantity;
    private BigDecimal price;

    protected EatInOrderLineItemDto() {
    }

    public EatInOrderLineItemDto(UUID menuId, long quantity, BigDecimal price) {
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
