package kitchenpos.menu.domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class MenuProduct {
    private final Long seq;
    private final UUID productId;
    private long quantity;
    private BigDecimal productPrice;

    public MenuProduct(UUID productId, long quantity, BigDecimal productPrice) {
        this(null, productId, quantity, productPrice);
    }

    public MenuProduct(Long seq, UUID productId, long quantity, BigDecimal productPrice) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }

    public void changeQuantity(long quantity) {
        this.quantity = quantity;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuProduct that = (MenuProduct) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(seq);
    }
}
