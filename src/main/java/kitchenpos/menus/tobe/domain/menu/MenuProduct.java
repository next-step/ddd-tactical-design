package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    public MenuProduct() {
    }

    public MenuProduct(UUID productId, BigDecimal price, long quantity) {
        this(null, productId, price, quantity);
    }

    public MenuProduct(Long seq, UUID productId, BigDecimal price, long quantity) {
        validate(quantity);
        this.seq = seq;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    private void validate(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal calculateAmount() {
        return this.price.multiply(BigDecimal.valueOf(this.quantity));
    }

    public void changePrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isSameProductId(UUID productId) {
        return this.productId.equals(productId);
    }

    public Long getSeq() {
        return seq;
    }

    public long getQuantity() {
        return this.quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProduct that = (MenuProduct) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
