package kitchenpos.menus.tobe.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.products.tobe.domain.ProductPrice;

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
    private ProductPrice price;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected MenuProduct() {
    }

    protected MenuProduct(UUID productId, ProductPrice price, long quantity) {
        this(null, productId, price, quantity);
    }

    public MenuProduct(Long seq, UUID productId, ProductPrice price, long quantity) {
        validateQuantity(quantity);
        this.seq = seq;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public static MenuProduct from(UUID productId, long quantity, ProductClient productClient) {
        ProductPrice productPrice = ProductPrice.from(productClient.productPrice(productId));
        return new MenuProduct(productId, productPrice, quantity);
    }

    public void changeProductPrice(ProductPrice price) {
        this.price = price;
    }

    public BigDecimal totalPrice() {
        return this.price.priceValue().multiply(BigDecimal.valueOf(this.quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProduct that = (MenuProduct) o;
        return quantity == that.quantity && Objects.equals(seq, that.seq) && Objects.equals(productId, that.productId) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, productId, price, quantity);
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

    public ProductPrice getPrice() {
        return price;
    }

    private void validateQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
    }
}
