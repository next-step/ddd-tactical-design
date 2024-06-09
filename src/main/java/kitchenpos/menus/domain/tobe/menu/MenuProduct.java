package kitchenpos.menus.domain.tobe.menu;

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
import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.domain.tobe.Product;

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
    private MenuQuantity quantity;

    @Transient
    private UUID productId;

    protected MenuProduct() {
    }

    public MenuProduct(Product product, MenuQuantity quantity) {
        this(null, product, quantity, product.getId());
    }

    public MenuProduct(Long seq, Product product, MenuQuantity quantity, UUID productId) {
        this.seq = seq;
        this.product = product;
        this.quantity = quantity;
        this.productId = productId;
    }

    public BigDecimal calculateSum() {
        return product.calculateSum(quantity.getQuantity());
    }

    public UUID getProductId() {
        return productId;
    }
}
