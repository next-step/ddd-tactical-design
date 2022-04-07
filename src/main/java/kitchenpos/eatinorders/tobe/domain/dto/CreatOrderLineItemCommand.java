package kitchenpos.eatinorders.tobe.domain.dto;

import java.math.BigDecimal;
import java.util.UUID;

public final class CreatOrderLineItemCommand {

    private UUID menuId;
    private BigDecimal price;
    private long quantity;

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }
}
