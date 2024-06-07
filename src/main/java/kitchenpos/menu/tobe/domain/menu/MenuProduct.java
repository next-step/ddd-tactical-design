package kitchenpos.menu.tobe.domain.menu;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_product")
@Entity(name = "newMenuProduct")
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "product_id", columnDefinition = "binary(16)")
    private UUID productId;

    @Embedded
    private MenuProductQuantity menuProductQuantity;

    @Embedded
    private ProductPrice productPrice;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, long quantity, BigDecimal price) {
        this.productId = productId;
        this.menuProductQuantity = new MenuProductQuantity(quantity);
        this.productPrice = new ProductPrice(price);
    }

    public BigDecimal getPrice() {
        return productPrice.getPrice();
    }

    public long getQuantity() {
        return this.menuProductQuantity.getQuantity();
    }

    public UUID getProductId() {
        return productId;
    }

    public boolean isSameProductId(UUID productId) {
        return this.productId.equals(productId);
    }

    public void changePrice(BigDecimal price) {
        this.productPrice = new ProductPrice(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProduct that = (MenuProduct) o;
        return Objects.equals(seq, that.seq) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(menuProductQuantity, that.menuProductQuantity) &&
                Objects.equals(productPrice, that.productPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, productId, menuProductQuantity, productPrice);
    }
}
