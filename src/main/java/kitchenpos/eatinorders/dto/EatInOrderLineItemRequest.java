package kitchenpos.eatinorders.dto;

import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.application.MenuLoader;
import kitchenpos.eatinorders.domain.EatInOrderLineItem;
import kitchenpos.eatinorders.domain.EatInOrderLineItemQuantity;

import java.math.BigDecimal;
import java.util.UUID;

public class EatInOrderLineItemRequest {
    private UUID menuId;
    private long quantity;
    private BigDecimal orderPrice;


    public EatInOrderLineItemRequest(UUID menuId, long quantity, BigDecimal price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.orderPrice = price;
    }

    public EatInOrderLineItemRequest(UUID menuId, long quantity, long price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.orderPrice = new BigDecimal(price);
    }

    public EatInOrderLineItemRequest() {
    }

    public EatInOrderLineItem toEntity(MenuLoader policy) {
        return new EatInOrderLineItem(
                menuId,
                new EatInOrderLineItemQuantity(quantity),
                new Price(orderPrice),
                policy
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

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
}
