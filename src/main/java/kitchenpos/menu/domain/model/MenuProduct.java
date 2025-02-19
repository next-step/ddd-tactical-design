package kitchenpos.menu.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuProduct {
    private final Long seq;
    private final UUID productId;
    private final long quantity;
    private BigDecimal productPrice;

    public MenuProduct(Long seq, UUID productId, long quantity, BigDecimal productPrice) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }

    public BigDecimal amount() {
        return productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getSeq() {
        return seq;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void changeMenuProductPrice(BigDecimal price) {
        this.productPrice = price;
    }
}
