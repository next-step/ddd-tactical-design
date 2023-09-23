package kitchenpos.menus.domain.menu;

import kitchenpos.common.domain.Price;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private Price productPrice;

    protected MenuProduct() {
    }

    public MenuProduct(final Long seq, final UUID productId, final long quantity, final Price price) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
        this.productPrice = price;
    }

    public Long getSeq() {
        return seq;
    }

    public long getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getProductPrice() {
        return productPrice.getPrice();
    }

    public void changeProductPrice(final Price productPrice) {
        if (productPrice == null) {
            throw new IllegalArgumentException("상품 가격이 비어 있습니다.");
        }

        if (!this.productPrice.equals(productPrice)) {
            this.productPrice = productPrice;
        }
    }
}
