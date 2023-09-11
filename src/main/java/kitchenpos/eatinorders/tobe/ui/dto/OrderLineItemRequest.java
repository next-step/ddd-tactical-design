package kitchenpos.eatinorders.tobe.ui.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItemRequest {
    private Long seq;

    private UUID menuId;

    private long quantity;

    private BigDecimal price;

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
