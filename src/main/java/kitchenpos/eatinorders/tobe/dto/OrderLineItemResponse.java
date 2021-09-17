package kitchenpos.eatinorders.tobe.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItemResponse {
    private Long seq;
    private long quantity;
    private BigDecimal price;
    private UUID menuId;
    private MenuResponse menu;

    protected OrderLineItemResponse() {}

    public Long getSeq() {
        return seq;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public MenuResponse getMenu() {
        return menu;
    }
}
