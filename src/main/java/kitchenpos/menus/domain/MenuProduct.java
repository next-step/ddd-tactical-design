package kitchenpos.menus.domain;

import kitchenpos.products.domain.ProductPrice;

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

    @Column(name = "product_id", columnDefinition = "binary(16)", nullable = false)
    private UUID productId;

    @Embedded
    private ProductPrice price;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, BigDecimal price, long quantity) {
        this(null, productId, price, quantity);
    }

    private MenuProduct(Long seq, UUID productId, BigDecimal price, long quantity) {
        this.seq = seq;
        this.productId = productId;
        this.price = new ProductPrice(price);
        this.quantity = quantity;
    }

    public void setSeq(final Long seq) {
        this.seq = seq;
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }

    public void changePrice(final BigDecimal price) {
        this.price.changePrice(price);
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal calculatePrice() {
        return price.getValue().multiply(BigDecimal.valueOf(quantity));
    }

}
