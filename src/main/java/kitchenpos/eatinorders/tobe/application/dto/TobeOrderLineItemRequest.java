package kitchenpos.eatinorders.tobe.application.dto;

import kitchenpos.eatinorders.tobe.domain.TobeOrderLineItem;

import java.math.BigDecimal;
import java.util.UUID;

public class TobeOrderLineItemRequest {
    private final long seq;
    private final UUID menuId;
    private final long quantity;
    private final BigDecimal price;

    public TobeOrderLineItemRequest(final long seq, final UUID menuId, final long quantity, final BigDecimal price) {
        this.seq = seq;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public static TobeOrderLineItemRequest from(TobeOrderLineItem item) {
        return new TobeOrderLineItemRequest(item.getSeq(), item.getMenuId(), item.getQuantity(), item.getPrice());
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
