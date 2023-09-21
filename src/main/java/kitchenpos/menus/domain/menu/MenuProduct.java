package kitchenpos.menus.domain.menu;

import kitchenpos.products.domain.Product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
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

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Transient
    private BigDecimal productPrice;

    protected MenuProduct() {
    }

    public MenuProduct(final UUID productId, final long quantity) {
        this.productId = productId;
        this.quantity = quantity;
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

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(final UUID productId) {
        this.productId = productId;
    }

    public void setProductPrice(final BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }
}
