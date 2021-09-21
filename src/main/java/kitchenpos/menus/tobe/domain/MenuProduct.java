package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Product;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_product")
@Entity(name = "tobeMenuProduct")
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "product_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private Product product;

    @Embedded
    private Quantity quantity;

    @Transient
    private UUID productId;

    protected MenuProduct() {}

    public MenuProduct(final Product product, final Quantity quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public MenuProduct(final UUID productId, final Quantity quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public MenuProduct(final long seq, final Product product, final Quantity quantity) {
        this.seq = seq;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getSeq() {
        return seq;
    }

    public Product getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity.getQuantity();
    }

    public UUID getProductId() {
        return productId;
    }
}
