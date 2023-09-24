package kitchenpos.eatinorders.tobe.application.dto;

import kitchenpos.eatinorders.tobe.domain.TobeOrderLineItem;

import java.math.BigDecimal;
import java.util.UUID;

public class TobeOrderLineItemRequest {
    private final Long seq;
    private final UUID menuId;
    private final long quantity;
    private final BigDecimal price;

    public TobeOrderLineItemRequest(final Long seq, final UUID menuId, final long quantity, final BigDecimal price) {
        this.seq = seq;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public static TobeOrderLineItemRequest from(TobeOrderLineItem item) {
        return new TobeOrderLineItemRequest(item.getSeq(), item.getMenuId(), item.getQuantity(), item.getPrice());
    }

    public TobeOrderLineItem toDomain() {
        return new TobeOrderLineItem(this.seq, this.quantity, this.menuId, this.price);
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
