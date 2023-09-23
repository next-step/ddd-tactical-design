package kitchenpos.orders.eatinorders.ui.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public class EatInOrderLineItemRestResponse {
    final private long seq;
    final private UUID menuId;
    final private long quantity;
    final private BigDecimal price;

    public EatInOrderLineItemRestResponse(long seq, UUID menuId, long quantity, BigDecimal price) {
        this.seq = seq;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }


    public long getSeq() {
        return seq;
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
