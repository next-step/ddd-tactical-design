package kitchenpos.eatinorders.dto;

import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.domain.EatInOrderLineItem;
import kitchenpos.eatinorders.domain.EatInOrderLineItemQuantity;
import kitchenpos.eatinorders.domain.MenuId;

import java.math.BigDecimal;
import java.util.UUID;

public class EatInOrderLineItemRequest {
    private UUID menuId;
    private long quantity;
    private BigDecimal price;

    public EatInOrderLineItemRequest(UUID menuId, long quantity, BigDecimal price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public EatInOrderLineItemRequest() {
    }

    public EatInOrderLineItem toEntity() {
        return new EatInOrderLineItem(
                new MenuId(menuId),
                new EatInOrderLineItemQuantity(quantity),
                new Price(price)
        );
    }
    public UUID getMenuId() {
        return menuId;
    }

    public void setMenuId(UUID menuId) {
        this.menuId = menuId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
