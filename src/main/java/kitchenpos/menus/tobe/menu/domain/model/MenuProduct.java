package kitchenpos.menus.tobe.menu.domain.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import kitchenpos.products.tobe.domain.model.ProductPrice;

@Table(name = "menu_product")
@Entity(name = "tobeMenuProduct")
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    private Quantity quantity;

    @Column(
        name = "product_id",
        nullable = false,
        columnDefinition = "varbinary(16)"
    )
    private UUID productId;

    @Transient
    private ProductPrice productPrice;

    protected MenuProduct() {
    }

    public MenuProduct(final Quantity quantity, final UUID productId, final ProductPrice productPrice) {
        this.quantity = quantity;
        this.productId = productId;
        this.productPrice = productPrice;
    }

    public Long getSeq() {
        return seq;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public ProductPrice getMenuProductPrice() {
        return productPrice.multiply(quantity);
    }

    public UUID getProductId() {
        return productId;
    }
}
