package kitchenpos.menus.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProductPrice;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    private UUID productId;

    private BigDecimal price;

    @Embedded
    private ProductQuantity quantity;

    protected MenuProduct() {
    }

    public MenuProduct(Product product, int quantity) {
        this(null, product.getId(), product.getPrice(), new ProductQuantity(quantity));
    }

    public MenuProduct(Product product, ProductQuantity quantity) {
        this(null, product.getId(), product.getPrice(), quantity);
    }

    public MenuProduct(Long seq, UUID productId, BigDecimal price, ProductQuantity quantity) {
        this.seq = seq;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal calculateSum() {
        return price.multiply(quantity.getQuantity());
    }

    public UUID getProductId() {
        return productId;
    }

    public void changePrice(ProductPrice price) {
        this.price = price.getPrice();
    }
}