package kitchenpos.menu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.UUID;
import kitchenpos.product.domain.model.Product;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "product_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private Product product;

    @Embedded
    private MenuProductQuantity quantity;

    @Transient
    private UUID productId;

    public MenuProduct() {
    }

    public MenuProduct(Product product, MenuProductQuantity quantity, UUID productId) {
        this.product = product;
        this.quantity = quantity;
        this.productId = productId;
    }

    public MenuProduct(long quantity, Product product, UUID productId) {
        this(product, new MenuProductQuantity(quantity), productId);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public long getInnerQuantity() {
        return quantity.getValue();
    }

    public UUID getProductId() {
        return productId;
    }
}
