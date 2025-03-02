package kitchenpos.eatinorder.application.service.model;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItemRequest {
    private Long seq;
    private long quantity;
    private UUID menuId;
    private BigDecimal price;

    public OrderLineItemRequest() {
    }

    public OrderLineItemRequest(long quantity, UUID menuId, BigDecimal price) {
        this.quantity = quantity;
        this.menuId = menuId;
        this.price = price;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(final Long seq) {
        this.seq = seq;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public void setMenuId(final UUID menuId) {
        this.menuId = menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }
}
