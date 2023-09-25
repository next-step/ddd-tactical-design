package kitchenpos.menu.tobe.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.product.tobe.domain.Product;
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

    public static MenuProduct of(Product product, long quantity) {
        return new MenuProduct(product.getId(), product.getPrice(), quantity);
    }

    public MenuPrice calculatePrice() {
        return price.multiply(quantity).toMenuPrice();
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
}
