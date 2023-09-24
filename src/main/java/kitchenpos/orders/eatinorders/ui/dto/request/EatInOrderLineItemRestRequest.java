package kitchenpos.orders.eatinorders.ui.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public class EatInOrderLineItemRestRequest {
    final private UUID menuId;
    final private long quantity;
    final private BigDecimal orderPrice;

    public EatInOrderLineItemRestRequest(UUID menuId, long quantity, BigDecimal price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.orderPrice = price;
    }

    public EatInOrderLineItemRestRequest(UUID menuId, long quantity, long price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.orderPrice = new BigDecimal(price);
    }


    public UUID getMenuId() {
        return menuId;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

}
