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

    public OrderLineItemResponse(final Long seq, final long quantity, final BigDecimal price, final UUID menuId, final MenuResponse menu) {
        this.seq = seq;
        this.quantity = quantity;
        this.price = price;
        this.menuId = menuId;
        this.menu = menu;
    }

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
