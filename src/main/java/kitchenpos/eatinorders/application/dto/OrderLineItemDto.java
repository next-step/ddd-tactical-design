package kitchenpos.eatinorders.application.dto;

import java.util.UUID;

public class OrderLineItemDto {
    private UUID menuId;
    private Long price;
    private long quantity;

    public OrderLineItemDto() {
    }

    public OrderLineItemDto(UUID menuId, Long price, long quantity) {
        this.menuId = menuId;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public Long getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }
}
