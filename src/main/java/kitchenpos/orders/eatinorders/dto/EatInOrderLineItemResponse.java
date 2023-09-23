package kitchenpos.orders.eatinorders.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class EatInOrderLineItemResponse {
    private long seq;
    private UUID menuId;
    private long quantity;
    private BigDecimal price;

    public EatInOrderLineItemResponse() {
    }

    public EatInOrderLineItemResponse(long seq, UUID menuId, long quantity, BigDecimal price) {
        this.seq = seq;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }


    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public void setMenuId(UUID menuId) {
        this.menuId = menuId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
