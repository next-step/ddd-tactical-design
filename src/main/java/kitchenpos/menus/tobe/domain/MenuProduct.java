package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Product;

import javax.persistence.*;
import java.util.UUID;

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
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private Product product;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Transient
    private UUID productId;

    protected MenuProduct() {
    }

    public MenuProduct(Long seq, UUID productId, long quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
    }

    public MenuProduct(Long seq, Product product, long quantity) {
        this.seq = seq;
        this.product = product;
        this.quantity = quantity;
    }

    public MenuProduct(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getSeq() {
        return seq;
    }

    public Product getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }
}
