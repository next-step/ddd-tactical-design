package kitchenpos.eatinorders.tobe.ui.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItemRequest {
    private final Long seq;

    private final UUID menuId;

    private final long quantity;

    private final BigDecimal price;

    public OrderLineItemRequest(Long seq, UUID menuId, long quantity, BigDecimal price) {
        this.seq = seq;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getSeq() {
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
