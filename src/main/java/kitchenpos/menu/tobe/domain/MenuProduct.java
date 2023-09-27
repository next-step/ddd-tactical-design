package kitchenpos.menu.tobe.domain;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.product.tobe.domain.ProductPrice;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    public static final int MIN_QUANTITY = 0;

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    private ProductPrice price;
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, ProductPrice price, long quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new IllegalArgumentException();
        }

        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public static MenuProduct of(UUID productId, ProductPrice price, long quantity) {
        return new MenuProduct(productId, price, quantity);
    }

    public MenuPrice calculatePrice() {
        return price.multiply(quantity).toMenuPrice();
    }

    public void changePrice(MenuPrice price) {
        this.price = price.toProductPrice();
    }

    public boolean equalsToProductId(UUID productId) {
        return this.productId.equals(productId);
    }

    public Long getSeq() {
        return seq;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuProduct)) {
            return false;
        }
        MenuProduct that = (MenuProduct) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
